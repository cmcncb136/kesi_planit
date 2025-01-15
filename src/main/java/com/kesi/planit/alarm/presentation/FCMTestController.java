package com.kesi.planit.alarm.presentation;

import com.kesi.planit.alarm.application.FCMService;
import com.kesi.planit.alarm.presentation.dto.MessageDto;
import com.kesi.planit.alarm.presentation.dto.NotificationDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("test")
public class FCMTestController {
    private final FCMService fcmService;

    @GetMapping("/fcm/data")
    public ResponseEntity<Void> fcm(@RequestParam("uid") String uid) {
        HashMap<String, String> testData = new HashMap<>();
        testData.put("uid", uid);
        testData.put("test", "test");

        fcmService.sendNotification(uid, MessageDto.builder()
                        .data(testData)
                        .notificationDto(NotificationDto.builder()
                                .body("test")
                                .title("Test")
                                .build())
                .build());

        return ResponseEntity.ok().build();
    }


}
