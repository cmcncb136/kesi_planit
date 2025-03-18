package com.kesi.planit.alarm.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class AlarmBasic extends AlarmData {
    private Long id;

    @Builder
    public AlarmBasic(Long id, Alarm alarm) {super(alarm); this.id = id; }

    @Override
    public HashMap<String, String> toAlarmData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", AlarmType.BASIC.name());

        return map;
    }
}
