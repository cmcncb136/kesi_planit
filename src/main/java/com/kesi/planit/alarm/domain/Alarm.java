package com.kesi.planit.alarm.domain;

import com.kesi.planit.alarm.presentation.dto.MessageDto;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Alarm {
    private long id;
    private User user;
    private String title;
    private String content;
    private AlarmType alarmType;
    private LocalDateTime createTime;
    private AlarmData data;

    public MessageDto toMessageDto() {
        return null;
    }


}
