package com.kesi.planit.alarm.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class AlarmBasic extends AlarmData {

    @Builder
    public AlarmBasic(Alarm alarm) {super(alarm);}

    @Override
    public HashMap<String, String> toAlarmData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", AlarmType.BASIC.name());

        return map;
    }
}
