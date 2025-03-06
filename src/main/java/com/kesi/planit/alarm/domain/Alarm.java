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
    private final Long id;
    private final User user;
    private final String title;
    private final String content;
    private final AlarmType alarmType;
    private final LocalDateTime createTime;

    public MessageDto toMessageDto() {
        return null;
    }

}
