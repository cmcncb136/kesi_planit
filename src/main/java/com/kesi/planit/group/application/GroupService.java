package com.kesi.planit.group.application;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.core.CommonResult;
import com.kesi.planit.group.Presentation.dto.GroupMakeInfoRequestDto;
import com.kesi.planit.group.application.repository.GroupAndUserRepo;
import com.kesi.planit.group.application.repository.GroupRepo;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.group.infrastructure.GroupAndUserJpaEntity;
import com.kesi.planit.group.infrastructure.GroupJpaEntity;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupService {
    private final GroupRepo groupRepo;
    private final GroupAndUserRepo groupAndUserRepo;

    //순환참조 주위!
    private final CalendarService calendarService;
    private final UserService userService;


    //그룹 추가
    //email로 조회하기 때문에 NFE 발생할 수 있음
    //Todo. 추후 예외처리
    public CommonResult addGroup(String makerUid, GroupMakeInfoRequestDto groupMakeInfoRequestDto) {
        List<User> users = groupMakeInfoRequestDto.inviteUserEmails.stream()
                .map(it -> userService.getUserByEmail(it)).toList();

        User maker = userService.getUserById(makerUid);

        //그룹을 만든 팀원에 추가
        users.add(maker);

        //캘린더 생성
        Calendar calendar = calendarService.save(Calendar.builder().build());

        //그룹 생성
        Group group = Group.builder()
                .groupName(groupMakeInfoRequestDto.groupName)
                .users(users.stream().collect(Collectors.toMap(it -> it.getUid(),
                        it -> Group.GroupInUser.builder()
                                .user(it)
                                .allowedSecurityLevel(0)
                                .build())))
                .groupCalendar(calendar)
                .maker(maker)
                .build();

        createGroup(group);

        //Todo. 반환 값을 어떻게 할지 고민해야됨
        return CommonResult.builder()
                .code(200)
                .msg(String.valueOf(group.getGid()))
                .success(true).build();
    }

    public Group getByCalendarId(Long calendarId) {
        return getById(groupRepo.findByCalendarId(calendarId).getGid()); //맵으로 변환하는 코드 길어서 귀찮아....
    }

    public Group getById(Long id) {
        GroupJpaEntity groupJpaEntity = groupRepo.findById(id);
        //1. Gid로 그룹과 유저과 연결되어 있는 정보를 가져온다.
        //2. 가져오 정보를 Map<String(uid), GroupInUser> 행태로 만든다.
        return groupJpaEntity.toModel(
                groupAndUserRepo.findByGid(groupJpaEntity.getGid()).stream().collect(Collectors.toMap(
                        groupAndUserJpa -> groupAndUserJpa.getUid(),
                        groupAndUserJpa -> groupAndUserJpa.mappingGroupToGroupInUser(
                                userService.getUserById(groupAndUserJpa.getUid()))
                )),
                calendarService.getById(groupJpaEntity.getCalendarId())
        );
    }


    public List<Group> getByUid(String uid) {
        return groupAndUserRepo.findByUid(uid).stream().map(groupAndUserJpaEntity
                -> getById(groupAndUserJpaEntity.getGid())).toList();
    }


    private Group save(Group group) {
        return groupRepo.save(GroupJpaEntity.from(group)).toModel(
                group.getUsers(), group.getGroupCalendar()
        );
    }

    public Group createGroup(Group group) {
        Group g = save(group);

        //유저 정보 연결을 초기화(저장) 및 최신화
        g.getUsers().values().stream().forEach(it ->
                groupAndUserRepo.save(GroupAndUserJpaEntity.from(g.getGid(), it))
        );
        return g;
    }

    public Group exitGroup(Group group, User user){
        //그룹에서 삭제
        group.getUsers().remove(user.getUid());
        //연결정보 삭제
        groupAndUserRepo.deleteByUidAndGid(user.getUid(), group.getGid());

        //Todo. 만약 같은 채팅방에 있는 경우을 대비해 알림을 해야됨

        return group;
    }

    public Group inviteGroup(Group group, String inviteUserEmail){
        User user = userService.getUserByEmail(inviteUserEmail);
        GroupAndUserJpaEntity groupAndUserJpaEntity = GroupAndUserJpaEntity.builder()
                .gid(group.getGid()).uid(user.getUid()).build();

        group.addUser(groupAndUserJpaEntity.mappingGroupToGroupInUser(user));
        groupAndUserRepo.save(groupAndUserJpaEntity);
        return group;
    }
}
