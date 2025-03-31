package com.kesi.planit.alarm.presentation;

import com.kesi.planit.alarm.application.AlarmStateService;
import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.presentation.dto.AlarmDataDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("alarm/state")
public class AlarmStateController {
    private final AlarmStateService alarmStateService;

    @GetMapping("/group")
    public ResponseEntity<List<AlarmDataDto>> getPendingAlarmInGroup(Long gid, HttpServletRequest request) {
        String uid = request.getAttribute("uid").toString();
        return alarmStateService.getPendingAlarmInGroup(uid, gid);
    }

//    @PatchMapping("/")
//    public ResponseEntity<Void> processAlarm(Long alarmId, HttpServletRequest request) {
//        String uid = request.getAttribute("uid").toString();
//        alarmStateService.processAlarm(alarmId);
//    }
}






















