package com.kesi.planit.schedule.application;

import com.kesi.planit.alarm.application.AlarmService;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.application.repository.ScheduleSecurityRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.schedule.infrastructure.ScheduleSecurityEntity;
import com.kesi.planit.schedule.presentation.dto.PersonalScheduleDto;
import com.kesi.planit.schedule.presentation.dto.RequestPersonalScheduleDto;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleSecurityService {
    private final ScheduleSecurityRepo scheduleSecurityRepo;
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final GroupService groupService;
    private final AlarmService alarmService;

    //그룹 일정 확인 후 시큐리티 설정
    public ResponseEntity<String> addGroupScheduleSecurity(String uid, Long scheduleId, SecurityLevel securityLevel) {
        Schedule schedule = scheduleService.getById(scheduleId);
        User user = userService.getUserById(uid);
        Group group = groupService.getByCalendarId(schedule.getSourceCalendar().getId());

        if(!group.getUsers().containsKey(uid))
            return ResponseEntity.badRequest().body("잘못된 접급입니다.");

        save(ScheduleSecurity.builder()
                .user(user)
                .schedule(schedule)
                .securityLevel(securityLevel)
                .build());

        return ResponseEntity.ok("ok");
    }

    public void otherSchedule(Long calendar, String uid){
        Group group = groupService.getByCalendarId(calendar);
        User user = userService.getUserById(uid);
    }

    public ResponseEntity<List<PersonalScheduleDto>> getPersonalSchedulesInMonth(String month, String uid) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(month);
        }catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(getScheduleMonthByUid(date, uid).stream().map(
                it -> PersonalScheduleDto.from(
                        it.getSchedule())).toList());
    }

    //개인 일정 추가
    public ResponseEntity<String> addPersonalSchedule(String uid, RequestPersonalScheduleDto personalScheduleDto) {
        //스케줄 도메인 객체 생성 및 저장
        Schedule schedule = null;
        User user = userService.getUserById(uid);

        try {
            schedule = scheduleService.save(personalScheduleDto.toModel(user));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        //security 정보 생성 및 저장
        scheduleSecurityRepo.save(
                ScheduleSecurityEntity.builder()
                        .securityLevel(personalScheduleDto.securityLevel)
                        .scheduleId(schedule.getId())
                        .uid(user.getUid()).build()
        );

        return ResponseEntity.ok("ok");
    }

    //그룹 캘린더에 스케줄 추가
    private void addGroupSchedule(Long calendarId, Schedule schedule) {
        //캘린더 id로 그룹을 찾는다.
        Group group = groupService.getByCalendarId(calendarId);

        //스케줄 정보 저장
        scheduleService.save(schedule);

        //각 사용자의 속한 그룹 최신화(현재 속한 그룹을 제외)
        alarmService.createGroupScheduleAlarm(group, schedule);
    }

    //특정 유저 스케줄 조회
    public List<ScheduleSecurity> getScheduleMonthByUid(LocalDate monthDate, String uid) {
        LocalDate startDate = LocalDate.of(monthDate.getYear(), monthDate.getMonthValue(), 1); //해당 월에 첫 날
        LocalDate endDate = LocalDate.of(monthDate.getYear(), monthDate.getMonthValue(), monthDate.lengthOfMonth()); //해당 월에 마지막 날

        List<ScheduleSecurityEntity> scheduleSecurityEntities
                = scheduleSecurityRepo.findSchedulesUidAndWithinDateRange(uid, startDate, endDate);

        User user = userService.getUserById(uid);

        return scheduleSecurityEntities.stream().map(it -> {
            Schedule schedule = scheduleService.getById(it.getScheduleId());
            return it.toModel(schedule, user);
        }).toList();
    }


    public ScheduleSecurity getById(Long id) {
        ScheduleSecurityEntity scheduleSecurityEntity = scheduleSecurityRepo.findById(id);
        User user = userService.getUserById(scheduleSecurityEntity.getUid());
        Schedule schedule = scheduleService.getById(scheduleSecurityEntity.getScheduleId());
        return scheduleSecurityEntity.toModel(schedule, user);
    }


    public ScheduleSecurity save(ScheduleSecurity scheduleSecurity) {
        return scheduleSecurityRepo.save(ScheduleSecurityEntity.from(scheduleSecurity))
                .toModel(scheduleSecurity.getSchedule(), scheduleSecurity.getUser());
    }
}