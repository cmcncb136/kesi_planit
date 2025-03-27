package com.kesi.planit.schedule.application;

import com.kesi.planit.alarm.application.AlarmService;
import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.presentation.dto.PersonalScheduleDto;
import com.kesi.planit.schedule.presentation.dto.RequestPersonalScheduleDto;
import com.kesi.planit.schedule.presentation.dto.RequestPersonalUpdateScheduleDto;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonalScheduleService {
    private final ScheduleSecurityService scheduleSecurityService;
    private final UserService userService;
    private final AlarmService alarmService;

    public ResponseEntity<List<PersonalScheduleDto>> getPersonalSchedulesInMonth(String monthDate, String uid) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(monthDate);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        User user = userService.getUserById(uid);
        List<ScheduleSecurity> personalSchedulesInMonth = scheduleSecurityService.getPersonalSchedulesInMonth(date, uid);
        return ResponseEntity.ok(personalSchedulesInMonth.stream().map(
                it -> PersonalScheduleDto.from(it, uid)).toList());
    }

    public ResponseEntity<Long> addPersonalSchedule(String uid, RequestPersonalScheduleDto personalScheduleDto) {
        //스케줄 도메인 객체 생성 및 저장
        User user = userService.getUserById(uid);
        ScheduleSecurity scheduleSecurity = personalScheduleDto.toModel(user);

        try {
            ScheduleSecurity savedScheduleSchedule = scheduleSecurityService.save(scheduleSecurity);
            return ResponseEntity.ok(savedScheduleSchedule.getSchedule().getId());
        } catch (Exception e) {
            System.out.println("schedule save exception : " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<PersonalScheduleDto> updatePersonalSchedule(String uid, RequestPersonalUpdateScheduleDto requestPersonalUpdateScheduleDto) {
        ScheduleSecurity scheduleSecurity = scheduleSecurityService.getByUidAndScheduleId(uid, requestPersonalUpdateScheduleDto.id);
        if(scheduleSecurity == null) return ResponseEntity.notFound().build();


        User user = userService.getUserById(uid);
//        if(!originalSchedule.getSourceCalendar().equals(user.getMyCalendar())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        ScheduleSecurity updateScheduleSecurity = requestPersonalUpdateScheduleDto.toModel(user, scheduleSecurity.getId());
        scheduleSecurity.editPossible(updateScheduleSecurity);

        ScheduleSecurity savedScheduleSecurity = scheduleSecurityService.save(scheduleSecurity);
        return ResponseEntity.ok(PersonalScheduleDto.from(savedScheduleSecurity, uid));
    }

    @Transactional
    public ResponseEntity<Void> removePersonalSchedule(String uid, Long scheduleId){
        scheduleSecurityService.removePersonalSchedule(uid, scheduleId);
        return ResponseEntity.ok().build();
    }
}
