package com.kesi.planit.group.application;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.core.CommonResult;
import com.kesi.planit.group.application.repository.GroupAndUserRepo;
import com.kesi.planit.group.application.repository.GroupRepo;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.group.infrastructure.GroupJpaEntity;
import com.kesi.planit.user.application.UserService;
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
}
