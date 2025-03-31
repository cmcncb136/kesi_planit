package com.kesi.planit.schedule.application;

import com.kesi.planit.alarm.application.AlarmService;
import com.kesi.planit.alarm.application.AlarmStateService;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.schedule.presentation.dto.GroupScheduleDto;
import com.kesi.planit.schedule.presentation.dto.RequestGroupScheduleDto;
import com.kesi.planit.schedule.presentation.dto.RequestGroupScheduleSecurityDto;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final AlarmService alarmService;
    private final AlarmStateService alarmStateService;

    public ResponseEntity<GroupScheduleDto> saveScheduleSecurity(String uid, RequestGroupScheduleSecurityDto requestGroupScheduleSecurityDto) {
        User user = userService.getUserById(uid);
        ScheduleSource scheduleSource = scheduleSourceService.getById(requestGroupScheduleSecurityDto.scheduleId);
        Group group = groupService.getByCalendarId(scheduleSource.getSourceCalendar().getId());

        if (!group.checkMember(user))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        ScheduleSecurity scheduleSecurity = requestGroupScheduleSecurityDto.toModel(scheduleSource);
        ScheduleSecurity savedSecurity = scheduleSecurityService.save(scheduleSecurity);

        //알림 처리
        alarmStateService.processGroupSchedule(user.getUid(), scheduleSource.getId());

        return ResponseEntity.ok(GroupScheduleDto.from(savedSecurity));
    }

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

        //user정보뢍 schedule 정보로 SchedulesSecurit 정보 찾기
        List<ScheduleSecurity> scheduleSecurities = new ArrayList<>();

        //NOTE. 생성되고 SCHEDULE SECURITY 설정 안 했으연 getByUserAndSchedule이 null일 수 있음!
        scheduleSources.forEach(scheduleSource -> {
            ScheduleSecurity scheduleSecurity = scheduleSecurityService.getByUserAndSchedule(user, scheduleSource);
            if(scheduleSecurity != null) scheduleSecurities.add(scheduleSecurity);
        });


        return ResponseEntity.ok(
                scheduleSecurities.stream().map(GroupScheduleDto::from).toList()
        );
    }

    public ResponseEntity<Long> addGroupSchedule(Long gid, RequestGroupScheduleDto requestGroupScheduleDto, String uid) {
        //캘린더 id로 그룹을 찾는다.
        Group group = groupService.getById(gid);
        User maker = userService.getUserById(uid);
        ScheduleSource schedule = requestGroupScheduleDto.toModel(maker, group.getGroupCalendar());

        if(!group.checkMember(uid)) //맴버가 아니라면
            return ResponseEntity.badRequest().build();

        //스케줄 소스 정보 저장
        scheduleSourceService.save(schedule);

        //그롭에 스케줄 추가를 회원들에게 알림
        alarmService.createGroupScheduleAlarm(group, schedule);

        return ResponseEntity.ok(schedule.getId());
    }
}














