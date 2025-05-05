package com.kesi.planit.alarm.presentation;

import com.kesi.planit.alarm.application.AlarmFacade;
import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.presentation.dto.AlarmDataDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("admin/alarm")
public class AdminAlarmController {
    private final AlarmFacade alarmFacade;

    @GetMapping("/all")
    public ResponseEntity<Page<AlarmDataDto>> getAlarm(HttpServletRequest request, int page, int size) {
        return alarmFacade.getAlarms((String)request.getAttribute("uid"), page, size);
    }
}
