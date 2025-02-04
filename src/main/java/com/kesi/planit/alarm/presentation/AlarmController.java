package com.kesi.planit.alarm.presentation;

import com.kesi.planit.alarm.application.AlarmService;
import com.kesi.planit.alarm.presentation.dto.AlarmDataDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("alarm")
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping("")
    public ResponseEntity<AlarmDataDto> getAlarm(@RequestParam("alarmId") Long alarmId) {
        return alarmService.getAlarmDataDtoById(alarmId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AlarmDataDto>> getAllAlarm(HttpServletRequest request) {
        return alarmService.getAlarmDataDtoByUid((String) request.getAttribute("uid"));
    }

    @GetMapping("/month")
    public ResponseEntity<List<AlarmDataDto>> getMonthAlarm(@RequestParam("month") String month, HttpServletRequest request) {
        return null;
    }
}
