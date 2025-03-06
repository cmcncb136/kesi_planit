package com.kesi.planit.alarm.presentation.dto;

import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.domain.AlarmType;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;

@Builder
public class AlarmDataDto {
    public long id;
    public String title;
    public String content;
    public AlarmType alarmType;
    public String createTime;
    public HashMap<String, String> data;

    public static AlarmDataDto toDto(Alarm alarm, AlarmData data){
        return AlarmDataDto.builder()
                .id(alarm.getId())
                .title(alarm.getTitle())
                .content(alarm.getContent())
                .alarmType(alarm.getAlarmType())
                .createTime(alarm.getCreateTime().toString())
                .data(data.toAlarmData())
                .build();
    }
}
