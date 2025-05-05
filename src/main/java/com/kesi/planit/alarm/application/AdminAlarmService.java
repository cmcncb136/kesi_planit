package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.presentation.dto.AlarmDataDto;
import com.kesi.planit.core.RoleCheck;
import com.kesi.planit.schedule.application.AdminScheduleService;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class AdminAlarmService {
    private final AlarmService alarmService;
    private final AlarmTypeService alarmTypeService;

    @RoleCheck
    public Page<AlarmDataDto> getAlarms(User user, Pageable pageable) {
        return alarmService.getAlarms(pageable).map(it -> AlarmDataDto.toDto(it, alarmTypeService.getAlarmTypeByAlarm(it)));
    }
}














