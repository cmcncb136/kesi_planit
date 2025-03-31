package com.kesi.planit.alarm.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Set;

@Getter
public class AlarmBasic extends AlarmData {
    private Long id;

    @Builder
    public AlarmBasic(Long id, Alarm alarm) {
        super(alarm, Set.of(AlarmType.BASIC));
        this.id = id;
    }

    @Override
    public HashMap<String, String> toAlarmData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", AlarmType.BASIC.name());

        return map;
    }

    @Override
    public boolean requireAction() {
        return false;
    }
}
