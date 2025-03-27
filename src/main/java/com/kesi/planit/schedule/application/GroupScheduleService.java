package com.kesi.planit.schedule.application;

import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.schedule.presentation.dto.GroupScheduleDto;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class GroupScheduleService {
    private final ScheduleSourceService scheduleSourceService;
    private final ScheduleSecurityService scheduleSecurityService;
    private final GroupService groupService;
    private final UserService userService;

    public ResponseEntity<List<GroupScheduleDto>> getAllGroupSchedulesInMonth(String monthDate, String uid) {
        LocalDate date;
        try {
            date = LocalDate.parse(monthDate);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        User user = userService.getUserById(uid);

        //group 그룹 조회
        List<Group> groups = groupService.getByUid(uid);

        //group있는 calendr로 group schedule 찾기
        ArrayList<ScheduleSource> scheduleSources = new ArrayList<>();

        LocalDate finalDate = date;
        groups.forEach(group -> {
            scheduleSources.addAll(
                    scheduleSourceService.getBySourceCalendarIdAndMonth(group.getGroupCalendar().getId(), finalDate)
            );
        });

        //user정보뢍 schedule 정보로 SchedulesSecurity 정보 차기
        List<ScheduleSecurity> scheduleSecurities =
                scheduleSources.stream().map( scheduleSource ->
                    scheduleSecurityService.getByUserAndSchedule(user, scheduleSource)
                ).toList();

        return ResponseEntity.ok(
                scheduleSecurities.stream().map(GroupScheduleDto::from).toList()
        );
    }

}
