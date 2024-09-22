package com.kesi.planit.group.application;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.core.CommonResult;
import com.kesi.planit.group.Presentation.dto.GroupMakeInfoRequestDto;
import com.kesi.planit.group.application.repository.GroupAndUserRepo;
import com.kesi.planit.group.application.repository.GroupRepo;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.group.infrastructure.GroupAndUserJpaEntity;
import com.kesi.planit.group.infrastructure.GroupAndUserJpaRepo;
import com.kesi.planit.group.infrastructure.GroupJpaEntity;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Todo. 이제 그룹 시작
@Service
@AllArgsConstructor
public class GroupService {
    private final GroupRepo groupRepo;
    private final GroupAndUserRepo groupAndUserRepo;
    //순환참조 주위!
    private final CalendarService calendarService;
    private final UserService userService;
    private final GroupAndUserJpaRepo groupAndUserJpaRepo;

    //Todo. 개인 캘린더와 그룹 캘린더를 구분할 필요가 있음
    //한 명도 그룹임으로 개인 캘린더 이면서 그룹 캘린더이다.
    //따라서 유저를 통해서를 참조 가능하고 그룹을 통해서도 참조 가능해야 한다.

    //그룹 추가
    //email로 조회하기 때문에 NFE 발생할 수 있음
    //Todo. 추후 예외처리
    public CommonResult addGroup(String makerUid, GroupMakeInfoRequestDto groupMakeInfoRequestDto) {
        List<User> users = groupMakeInfoRequestDto.inviteUserEmails.stream()
                .map(it -> userService.getUserByEmail(it)).toList();

        User maker = userService.getUserByEmail(makerUid);

        //그룹을 만든 팀원에 추가
        users.add(maker);

        //캘린더 생성
        Calendar calendar = calendarService.save(Calendar.builder().build());

        //그룹 생성
        Group group = save(Group.builder()
                .groupName(groupMakeInfoRequestDto.groupName)
                .users(users.stream().collect(Collectors.toMap(it -> it.getUid(),
                        it -> Group.GroupInUser.builder()
                                .user(it)
                                .open(false)
                                .access(false)
                                .build())))
                .groupCalendar(calendar)
                .maker(maker)
                .build());

        //Todo. 반환 값을 어떻게 할지 고민해야됨
        return null;
    }

    public Group getByCalendarId(Long calendarId) {
        return getById(groupRepo.findByCalendarId(calendarId).getGid()); //맵으로 변환하는 코드 길어서 귀찮아....
    }

    public Group getById(Long id){
        GroupJpaEntity groupJpaEntity = groupRepo.findById(id);
        //1. Gid로 그룹과 유저과 연결되어 있는 정보를 가져온다.
        //2. 가져오 정보를 Map<String(uid), GroupInUser> 행태로 만든다.
        return groupJpaEntity.toModel(
                groupAndUserRepo.findByGid(groupJpaEntity.getGid()).stream().collect(Collectors.toMap(
                        GroupAndUserJpaEntity::getUid,
                        groupAndUserJpa -> groupAndUserJpa.mappingGroupToGroupInUser(
                                userService.getUserById(groupAndUserJpa.getUid()))
                )),
                calendarService.getById(groupJpaEntity.getCalendarId())
        );
    }


    public List<Group> getByUid(String uid) {
        //Group Entity 가져오기
        List<GroupJpaEntity> groupJpaEntities = groupAndUserRepo.findByUid(uid).stream().map(
                it -> groupRepo.findById(it.getGid())
        ).toList();

        //도메인 모델로 매핑시키기
        return groupJpaEntities.stream().map(it -> it.toModel(
                groupAndUserRepo.findByGid(it.getGid()).stream().collect(Collectors.toMap(
                        GroupAndUserJpaEntity::getUid,
                        groupAndUserJpa -> groupAndUserJpa.mappingGroupToGroupInUser(
                                userService.getUserById(groupAndUserJpa.getUid()))
                )),
                calendarService.getById(it.getCalendarId())
        )).toList();
    }



    public Group save(Group group) {
        Group g = groupRepo.save(GroupJpaEntity.from(group)).toModel(
                group.getUsers()
                , group.getGroupCalendar());

        //유저 정보 연결을 초기화(저장) 및 최신화
        g.getUsers().values().stream().forEach(it -> {
                   Group.GroupInUser groupInUser = groupAndUserRepo.save(GroupAndUserJpaEntity.
                            from(g.getGid(), it)).mappingGroupToGroupInUser(it.getUser());

                   g.getUsers().put(groupInUser.getUser().getUid(), groupInUser);
                }
                );

        return g;
    }
}
