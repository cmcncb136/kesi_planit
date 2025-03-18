package com.kesi.planit.alarm.presentation.dto;

import com.google.firebase.messaging.Notification;
import com.kesi.planit.alarm.domain.Alarm;
import lombok.Builder;

@Builder
public class NotificationDto {
    public String title;
    public String body;
    public String image;

    public Notification toNotification() {
        return Notification.builder()
                .setBody(body)
                .setTitle(title)
                .setImage(image)
                .build();
    }

    public static NotificationDto toNotification(Alarm alarm) {
        return NotificationDto.builder()
                .title(alarm.getTitle())
                .body(alarm.getContent()).build();
    }
}
