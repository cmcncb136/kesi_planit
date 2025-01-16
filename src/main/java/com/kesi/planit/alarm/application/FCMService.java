package com.kesi.planit.alarm.application;

import com.google.firebase.messaging.*;
import com.google.rpc.context.AttributeContext;
import com.kesi.planit.alarm.domain.Device;
import com.kesi.planit.alarm.presentation.dto.MessageDto;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FCMService {
    private final DeviceService deviceService;
    private final FirebaseMessaging firebaseMessaging;

    public ResponseEntity<String> addFCMToken(String token, String uid) {
        Device device = Device.builder().fcmToken(token).uid(uid).build();
        device = deviceService.save(device);
        return ResponseEntity.ok(device.getFcmToken());
    }

    public void sendNotification(String uid, MessageDto messageDto) {
        List<Device> deviceList = deviceService.getByUid(uid); //사용자에 토큰 목록을 가져온다.
        List<String> fcmTokenList = deviceList.stream().map(Device::getFcmToken).toList();

        //Todo. 만료된 토큰은 삭제가 필요하다.
        //계정에 연결된 토큰 개수만큼 전송
        fcmTokenList.forEach(fcmToken -> {
            String msg = null;
            try {
                msg = firebaseMessaging.send(messageDto.toMessage(fcmTokenList.get(0)));
            } catch (FirebaseMessagingException e) {
                System.out.println("FCM Token Exception : " + e.getMessage());
            }
            System.out.println("msg: " + msg);
        });
    }
}
