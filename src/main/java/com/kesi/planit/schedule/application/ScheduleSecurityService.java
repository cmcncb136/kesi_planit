package com.kesi.planit.schedule.application;

import com.kesi.planit.alarm.application.AlarmService;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.application.repository.ScheduleSecurityRepo;
import com.kesi.planit.schedule.domain.FilteredSchedule;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.schedule.infrastructure.ScheduleSecurityEntity;
import com.kesi.planit.schedule.presentation.dto.*;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleSecurityService {
    private final ScheduleSecurityRepo scheduleSecurityRepo;
    private final ScheduleSourceService scheduleService;
    private final UserService userService;
    private final GroupService groupService;
    private final AlarmService alarmService;

    @Transactional
    public void removePersonalSchedule(String uid, Long scheduleId) {
        ScheduleSource schedule = scheduleService.getById(scheduleId);
        User user = userService.getUserById(uid);
        if(schedule == null) throw new IllegalArgumentException("schedule not found");

        schedule.editPossible(user.getMyCalendar()); //삭제 가능한지 확인

        deleteBySchedule(schedule); //해당 스케줄과 연결게 security 정보를 삭제
        scheduleService.deleteById(scheduleId); //스케줄 삭제
    }


    //그룹 일정 확인 후 시큐리티 설정
    public ResponseEntity<String> setGroupScheduleSecurity(String uid, Long scheduleId, SecurityLevel securityLevel) {
        ScheduleSource schedule = scheduleService.getById(scheduleId);
        User user = userService.getUserById(uid);
        Group group = groupService.getByCalendarId(schedule.getSourceCalendar().getId());

        if(!group.getUsers().contains(uid))
            return ResponseEntity.badRequest().body("잘못된 접급입니다.");

        save(ScheduleSecurity.builder()
                .user(user)
                .schedule(schedule)
                .securityLevel(securityLevel)
                .build());

        return ResponseEntity.ok("ok");
    }


    //그룹 일정 조회
    public ResponseEntity<List<GroupScheduleDto>> getGroupSchedulesAndMonth(String month, String uid, Long gid) {
        LocalDate date = null;
        try {
             date = LocalDate.parse(month);
        }catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        Group group = groupService.getById(gid);

        if(group.checkMember(uid)) //그룹 유저가 아니라면
            return ResponseEntity.badRequest().build();

        return null;

//        return ResponseEntity.ok().body(scheduleService.getBySourceCalendarIdAndMonth(group.getGroupCalendar().getId(), date)
//                .stream().map(GroupScheduleDto::from).toList());
    }



    //유저 스케줄 조회
    //전체 조회는 허락하지 않음
    //Todo. 다른 앱들에서는 많은 양에 데이터 동기화 작업을 어떻게 하는지 조사할필요 있음.
    public List<ScheduleSecurity> getScheduleSecurityMonthByUid(LocalDate monthDate, String uid) {
        LocalDate startDate = LocalDate.of(monthDate.getYear(), monthDate.getMonthValue(), 1); //해당 월에 첫 날
        LocalDate endDate = LocalDate.of(monthDate.getYear(), monthDate.getMonthValue(), monthDate.lengthOfMonth()); //해당 월에 마지막 날

        List<ScheduleSecurityEntity> scheduleSecurityEntities
                = scheduleSecurityRepo.findSchedulesUidAndWithinDateRange(uid, startDate, endDate);

        User user = userService.getUserById(uid);

        return scheduleSecurityEntities.stream().map(it -> {
            ScheduleSource schedule = scheduleService.getById(it.getScheduleId());
            return it.toModel(schedule, user);
        }).toList();
    }


    public List<ScheduleSecurity> getPersonalSchedulesInMonth(LocalDate monthDate, String uid) {
        User user = userService.getUserById(uid);

        List<ScheduleSource> schedules = scheduleService.getBySourceCalendarIdAndMonth(user.getMyCalendar().getId(), monthDate);
        return schedules.stream().map(it -> getByUserAndSchedule(user, it)).toList();
    }

    private ScheduleSecurity getById(Long id) {
        ScheduleSecurityEntity scheduleSecurityEntity = scheduleSecurityRepo.findById(id);
        User user = userService.getUserById(scheduleSecurityEntity.getUid());
        ScheduleSource schedule = scheduleService.getById(scheduleSecurityEntity.getScheduleId());

        return scheduleSecurityEntity.toModel(schedule, user);
    }


    public ScheduleSecurity getByUserAndSchedule(User user, ScheduleSource schedule) {
        ScheduleSecurityEntity entity = scheduleSecurityRepo.findByUidAndScheduleId(user.getUid(), schedule.getId());
        return entity == null ? null : entity.toModel(schedule, user);
    }

    public ScheduleSecurity getByUidAndScheduleId(String uid, Long scheduleId) {
        User user = userService.getUserById(uid);
        ScheduleSource schedule = scheduleService.getById(scheduleId);

        return getByUserAndSchedule(user, schedule);
    }

    public ScheduleSecurity save(ScheduleSecurity scheduleSecurity) {
        ScheduleSource schedule = scheduleService.save(scheduleSecurity.getSchedule());
        ScheduleSecurityEntity scheduleSecurityEntity = ScheduleSecurityEntity.builder()
                .scheduleId(schedule.getId())
                .securityLevel(scheduleSecurity.getSecurityLevel())
                .uid(scheduleSecurity.getUser().getUid())
                .id(scheduleSecurity.getId())
                .build();
        //Todo. 추후 로직 최적화 필요
        return scheduleSecurityRepo.save(scheduleSecurityEntity)
                .toModel(schedule, scheduleSecurity.getUser());
    }


    public void deleteBySchedule(ScheduleSource schedule) {
        scheduleSecurityRepo.deleteBySchedule(schedule.getId());
    }
}