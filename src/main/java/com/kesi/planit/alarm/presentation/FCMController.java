package com.kesi.planit.alarm.presentation;

import com.kesi.planit.alarm.application.DeviceService;
import com.kesi.planit.alarm.application.FCMService;
import com.kesi.planit.alarm.domain.Device;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("fcm-token")
public class FCMController {
    private final FCMService fcmService;

    @PostMapping("")
    public ResponseEntity<String> saveFCMToken(@RequestParam("token") String token, HttpServletRequest request) {
        return  fcmService.addFCMToken(token, (String)request.getAttribute("uid"));
    }
}
