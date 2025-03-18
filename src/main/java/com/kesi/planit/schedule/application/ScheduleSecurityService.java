package com.kesi.planit.schedule.application;

import com.kesi.planit.alarm.application.AlarmService;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.application.repository.ScheduleSecurityRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.schedule.infrastructure.ScheduleSecurityEntity;
import com.kesi.planit.schedule.infrastructure.ScheduleSecurityJpaRepo;
import com.kesi.planit.schedule.presentation.dto.*;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final GroupService groupService;
    private final AlarmService alarmService;

    public ResponseEntity<PersonalScheduleDto> updatePersonalSchedule(String uid, RequestPersonalUpdateScheduleDto requestPersonalUpdateScheduleDto) {
        ScheduleSecurity scheduleSecurity = getByUidAndScheduleId(uid, requestPersonalUpdateScheduleDto.id);
        if(scheduleSecurity == null) return ResponseEntity.notFound().build();

        //스케줄 정보 변경
        Schedule originalSchedule = scheduleSecurity.getSchedule();

        User user = userService.getUserById(uid);
        if(!originalSchedule.getSourceCalendar().equals(user.getMyCalendar())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Schedule updateSchedule = requestPersonalUpdateScheduleDto.toModel(originalSchedule.getMaker());
        Schedule savedSchedule = scheduleService.save(updateSchedule);

        ScheduleSecurity updateSecuritySchedule = ScheduleSecurity.builder()
                .schedule(savedSchedule)
                .id(savedSchedule.getId())
                .securityLevel(requestPersonalUpdateScheduleDto.securityLevel)
                .user(user)
                .build();

        ScheduleSecurity savedSecuritySchedule = save(updateSecuritySchedule);

        return ResponseEntity.ok(PersonalScheduleDto.from(savedSecuritySchedule, user.getUid()));
    }

    @Transactional
    public ResponseEntity<Void> removePersonalSchedule(String uid, Long scheduleId) {
        Schedule schedule = scheduleService.getById(scheduleId);
        if(schedule == null) return ResponseEntity.notFound().build();

        User user = userService.getUserById(uid);
        if(!schedule.getSourceCalendar().equals(user.getMyCalendar())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        deleteBySchedule(schedule);
        scheduleService.deleteById(scheduleId);

        return ResponseEntity.ok().build();
    }

    //그룹 일정 확인 후 시큐리티 설정
    public ResponseEntity<String> setGroupScheduleSecurity(String uid, Long scheduleId, SecurityLevel securityLevel) {
        Schedule schedule = scheduleService.getById(scheduleId);
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

    //개인 일정 조회
    public ResponseEntity<List<PersonalScheduleDto>> getPersonalSchedulesAndMonth(String monthDate, String uid) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(monthDate);
        }catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(getScheduleSecurityMonthByUid(date, uid).stream().map(
                it -> PersonalScheduleDto.from(it, uid)).toList());
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


        return ResponseEntity.ok().body(scheduleService.getBySourceCalendarIdAndMonth(group.getGroupCalendar().getId(), date)
                .stream().map(GroupScheduleDto::from).toList());
    }

    //그룹 유저들의 스케줄 일정을 월별 조회
    public ResponseEntity<List<GroupUserScheduleDto>> getGroupUserSchedulesInMonth(String monthDate, String uid, Long gid) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(monthDate);
        }catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        Group group = groupService.getById(gid);

        if(!group.checkMember(uid)) //그룹 유저가 아니라면
            return ResponseEntity.badRequest().build();

        LocalDate finalDate = date;
        List<Schedule> schedules = new ArrayList<>();
        group.exitUser(uid); //조회하는 유저를 제외한 유저를 제외해야되기 때문에 조회하는 유저를 임시로 제외

        group.getUserList().forEach(user -> { //모든 유저에 대해서
            //일정을 조회
            getScheduleSecurityMonthByUid(finalDate, user.getUid()).forEach(scheduleSecurity -> {
                Schedule schedule = scheduleSecurity.getSchedule(group); //유저가 설정한 보안등급에 따라서 정보를 가져옴

                if(!schedule.getSourceCalendar().equals(group.getGroupCalendar())) //같이 공유하고 있는 그룹 스케줄 정보는 제외
                    schedules.add(schedule);
            });
        });

        return ResponseEntity.ok(schedules.stream().map(GroupUserScheduleDto::from).toList());
    }


    //개인 일정 추가
    public ResponseEntity<Long> addPersonalSchedule(String uid, RequestPersonalScheduleDto personalScheduleDto) {
        //스케줄 도메인 객체 생성 및 저장
        Schedule schedule = null;
        User user = userService.getUserById(uid);

        try {
            schedule = scheduleService.save(personalScheduleDto.toModel(user));
        }catch (Exception e) {
            System.out.println("schedule save exception : " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        //security 정보 생성 및 저장
        ScheduleSecurity scheduleSecurity = save(ScheduleSecurity.builder()
                .securityLevel(personalScheduleDto.getSecurityLevel())
                .schedule(schedule)
                .user(user)
                .build());

        return ResponseEntity.ok(scheduleSecurity.getSchedule(user).getId());
    }

    //그룹 캘린더에 스케줄 추가
    public  ResponseEntity<String> addGroupSchedule(Long gid, RequestGroupScheduleDto requestGroupScheduleDto, String uid) {
        //캘린더 id로 그룹을 찾는다.
        Group group = groupService.getById(gid);
        User maker = userService.getUserById(uid);
        Schedule schedule = requestGroupScheduleDto.toModel(maker, group.getGroupCalendar());

        if(!group.checkMember(uid)) //맴버가 아니라면
            return ResponseEntity.badRequest().build();

        //스케줄 정보 저장
        scheduleService.save(schedule);

        //그롭에 스케줄 추가를 회원들에게 알림
        alarmService.createGroupScheduleAlarm(group, schedule);

        return ResponseEntity.ok("ok");
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
            Schedule schedule = scheduleService.getById(it.getScheduleId());
            return it.toModel(schedule, user);
        }).toList();
    }


    private ScheduleSecurity getById(Long id) {
        ScheduleSecurityEntity scheduleSecurityEntity = scheduleSecurityRepo.findById(id);
        User user = userService.getUserById(scheduleSecurityEntity.getUid());
        Schedule schedule = scheduleService.getById(scheduleSecurityEntity.getScheduleId());

        return scheduleSecurityEntity.toModel(schedule, user);
    }

    private ScheduleSecurity getByUidAndScheduleId(String uid, Long scheduleId) {
        ScheduleSecurityEntity entity = scheduleSecurityRepo.findByUidAndScheduleId(uid, scheduleId);
        User user = userService.getUserById(entity.getUid());
        Schedule schedule = scheduleService.getById(entity.getScheduleId());

        return entity.toModel(schedule, user);
    }

    public ScheduleSecurity save(ScheduleSecurity scheduleSecurity) {
        return scheduleSecurityRepo.save(ScheduleSecurityEntity.from(scheduleSecurity))
                .toModel(scheduleSecurity.getSchedule(), scheduleSecurity.getUser());
    }


    public void deleteBySchedule(Schedule schedule) {
        scheduleSecurityRepo.deleteBySchedule(schedule.getId());
    }
}