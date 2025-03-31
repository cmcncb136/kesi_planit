package com.kesi.planit.schedule.application;

import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.domain.FilteredSchedule;
import com.kesi.planit.schedule.presentation.dto.GroupUserScheduleDto;
import com.kesi.planit.user.application.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OtherScheduleService {
    private final GroupService groupService;
    private final ScheduleSecurityService scheduleSecurityService;

    //그룹 유저들의 스케줄 일정을 월별 조회
    public ResponseEntity<List<GroupUserScheduleDto>> getOtherSchedulesInMonth(String monthDate, String uid, Long gid) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(monthDate);
        }catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        Group group = groupService.getById(gid);

        if(group == null)
            return ResponseEntity.notFound().build();
        if(!group.checkMember(uid)) //그룹 유저가 아니라면
            return ResponseEntity.badRequest().build();

        LocalDate finalDate = date;
        List<FilteredSchedule> schedules = new ArrayList<>();
        group.exitUser(uid); //조회하는 유저를 제외한 유저를 제외해야되기 때문에 조회하는 유저를 임시로 제외

        group.getUserList().forEach(user -> { //모든 유저에 대해서
            //일정을 조회
            scheduleSecurityService.getScheduleSecurityMonthByUid(finalDate, user.getUid()).forEach(scheduleSecurity -> {
                FilteredSchedule schedule = FilteredSchedule.toFilteredSchedule(scheduleSecurity, group, user); //유저가 설정한 보안등급에 따라서 정보를 가져옴

                if(!schedule.getSourceCalendar().equals(group.getGroupCalendar())) //같이 공유하고 있는 그룹 스케줄 정보는 제외
                    schedules.add(schedule);
            });
        });

        return ResponseEntity.ok(schedules.stream().map(GroupUserScheduleDto::from).toList());
    }
}
