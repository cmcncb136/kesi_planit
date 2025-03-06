package com.kesi.planit.group.application;

import com.kesi.planit.alarm.application.AlarmCRUDService;
import com.kesi.planit.alarm.application.AlarmService;
import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.group.Presentation.dto.GroupDto;
import com.kesi.planit.group.Presentation.dto.GroupMakeInfoRequestDto;
import com.kesi.planit.group.Presentation.dto.GroupSimpleDto;
import com.kesi.planit.group.application.repository.GroupAndUserRepo;
import com.kesi.planit.group.application.repository.GroupRepo;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.group.domain.GroupUserMap;
import com.kesi.planit.group.infrastructure.GroupAndUserJpaEntity;
import com.kesi.planit.group.infrastructure.GroupJpaEntity;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final AlarmService alarmService;


    //그룹 추가
    //email로 조회하기 때문에 NFE 발생할 수 있음
    //Todo. 추후 예외처리
    public ResponseEntity<Long> createGroup(String makerUid, GroupMakeInfoRequestDto groupMakeInfoRequestDto) {
        List<User> users;

        try {
            users = groupMakeInfoRequestDto.inviteUserEmails.stream()
                    .map(userService::getUserByEmail)
                    .collect(Collectors.toCollection(ArrayList::new));
        }catch (NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
        User maker = userService.getUserById(makerUid);

        //그룹을 만든 팀원에 추가
        users.add(maker);

        //캘린더 생성
        Calendar calendar = calendarService.save(Calendar.builder().build());

        //그룹 생성
        //allowedSecurityLevel 5인 경우 설정되지 않았음을 의미한다고 가정
        Group group = Group.builder()
                .groupName(groupMakeInfoRequestDto.groupName)
                .users(new GroupUserMap(users.stream().collect(Collectors.toMap(it -> it.getUid(),
                        it -> Group.GroupInUser.builder()
                                .user(it)
                                .allowedSecurityLevel(SecurityLevel.HIGH)
                                .build()))))
                .groupCalendar(calendar)
                .maker(maker)
                .build();

        group = save(group);
        saveUserRelation(group);

        //Todo. 상대방에 초대되었다는 메시지를 보내야됨.
        alarmService.createGroupAlarm(group);

        return ResponseEntity.ok().body(group.getGid());
    }

    public Group getByCalendarId(Long calendarId) {
        return getById(groupRepo.findByCalendarId(calendarId).getGid()); //맵으로 변환하는 코드 길어서 귀찮아....
    }

    public Group getById(Long id) {
        GroupJpaEntity groupJpaEntity = groupRepo.findById(id);
        //1. Gid로 그룹과 유저과 연결되어 있는 정보를 가져온다.
        //2. 가져오 정보를 Map<String(uid), GroupInUser> 행태로 만든다.
        return groupJpaEntity.toModel(
                new GroupUserMap(groupAndUserRepo.findByGid(groupJpaEntity.getGid()).stream().collect(Collectors.toMap(
                        GroupAndUserJpaEntity::getUid,
                        groupAndUserJpa -> groupAndUserJpa.mappingGroupToGroupInUser(
                                userService.getUserById(groupAndUserJpa.getUid()))
                ))),
                calendarService.getById(groupJpaEntity.getCalendarId())
        );
    }

    public void checkGroup(GroupMakeInfoRequestDto dto, User uid){
        User user = userService.getUserById(uid.getUid());
        ArrayList<User> userList = dto.inviteUserEmails.stream().map(userService::getUserByEmail)
                .collect(Collectors.toCollection(ArrayList::new));

        userList.add(user);

        List<Group> groupList = getByUid(user.getUid());

        groupList.forEach(group -> {
            boolean notFound = false;

            for(User u : userList){
                if(!group.getUsers().contains(u.getUid())){
                    notFound = true;
                    break;
                }
            }

            if(!notFound && group.userNumber() == userList.size()){

            }
        });
    }


    public ResponseEntity<GroupDto> getGroupDto(Long gid, String uid){
        Group group = getById(gid);

        if(!group.checkMember(uid)){ //그룹에 속하지 않은 맴버라면
            return ResponseEntity.badRequest().build();  //정보를 주지 않는다.
        }

        return ResponseEntity.ok(GroupDto.from(group));
    }

    public ResponseEntity<List<GroupSimpleDto>> getGroupSimpleDto(String uid){
        List<Group> inGroups = getByUid(uid);
        return ResponseEntity.ok(inGroups.stream().map(GroupSimpleDto::from).toList());
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

    public void saveUserRelation(Group g) {
        //유저 정보 연결을 초기화(저장) 및 최신화
        g.getGroupInUserList().forEach(it ->
                groupAndUserRepo.save(GroupAndUserJpaEntity.from(g.getGid(), it))
        );
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
