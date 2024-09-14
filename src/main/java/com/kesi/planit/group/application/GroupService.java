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

//Todo. 이제 그룹 시작
@Service
@AllArgsConstructor
public class GroupService {
    private final GroupRepo groupRepo;
    private final GroupAndUserRepo groupAndUserRepo;
    //순환참조 주위!
    private final CalendarService calendarService;
    private final UserService userService;

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
                .users(users)
                .groupCalendar(calendar)
                .maker(maker)
                .build());

        //Todo. 반환 값을 어떻게 할지 고민해야됨
        return null;
    }


    public List<Group> getByUid(String uid) {
        //Group Entity 가져오기
        List<GroupJpaEntity> groupJpaEntities = groupAndUserRepo.findByUid(uid).stream().map(
                it -> groupRepo.findById(it.getGid())
        ).toList();

        //도메인 모델로 매핑시키기
        return groupJpaEntities.stream().map(it -> it.toModel(
                userService.getByGid(it.getGid()), calendarService.getById(it.getCalendarId())
        )).toList();
    }

    public Group save(Group group) {
        Group g = groupRepo.save(GroupJpaEntity.from(group)).toModel(group.getUsers(), group.getGroupCalendar());;

        try {
            //유저 정보 연결을 초기화(저장) 및 최신화
            List<String> uidList = groupAndUserRepo.findByGid(g.getGid()).stream()
                    .map(it -> it.getUid()).toList();

            for (User user : g.getUsers()) {
                if (!uidList.contains(user.getUid()))
                    groupAndUserRepo.save(GroupAndUserJpaEntity.builder()
                                    .gid(g.getGid())
                                    .access(false)
                                    .open(false)
                                    .uid(user.getUid())
                            .build());
            }
        }catch (NullPointerException e){
            g.getUsers().stream().map(it -> groupAndUserRepo.save(GroupAndUserJpaEntity.builder()
                    .gid(g.getGid())
                    .access(false)
                    .open(false)
                    .uid(it.getUid())
                    .build()));
        }

        return g;
    }
}
