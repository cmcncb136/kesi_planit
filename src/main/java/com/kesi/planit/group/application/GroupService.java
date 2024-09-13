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
