package com.kesi.planit.alarm.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

@Getter
@Builder
public class AlarmBasic implements AlarmData{

    @Override
    public HashMap<String, String> toAlarmData() {
        return new HashMap<>();
    }
}
