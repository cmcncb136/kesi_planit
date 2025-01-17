package com.kesi.planit.alarm.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class AlarmBasic implements AlarmData {

    @Override
    public void setAlarmId(long alarmId) {
    }

    @Override
    public HashMap<String, String> toAlarmData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", AlarmType.BASIC.name());

        return map;
    }
}
