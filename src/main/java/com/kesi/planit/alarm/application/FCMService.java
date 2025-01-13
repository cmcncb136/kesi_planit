package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.domain.Device;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FCMService {
    private final DeviceService deviceService;

    public ResponseEntity<String> addFCMToken(String token, String uid){
        Device device = Device.builder().fcmToken(token).uid(uid).build();
        device = deviceService.save(device);
        return ResponseEntity.ok(device.getFcmToken());
    }

}
