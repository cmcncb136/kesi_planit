package com.kesi.planit.alarm.presentation.dto;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import lombok.Builder;
import org.antlr.v4.runtime.Token;

import java.util.HashMap;
import java.util.List;

@Builder
public class MessageDto {
    public NotificationDto notificationDto;
    public HashMap<String, String> data;

    public Message toMessage(String token) {
        return Message.builder()
                .setNotification(notificationDto != null ? notificationDto.toNotification() : null)
                .setToken(token)
                .putAllData(data)
                .build();
    }

    public MulticastMessage toMulticastMessage(List<String> tokens) {
        return MulticastMessage.builder()
                .setNotification(notificationDto != null ? notificationDto.toNotification() : null)
                .putAllData(data)
                .addAllTokens(tokens)
                .build();
    }
}
