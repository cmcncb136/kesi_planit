package com.kesi.planit.alarm.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

@Getter
@Builder
public class AlarmGroup implements AlarmData {
    private long id;
    private long gid;
    private long alarmId;

    @Override
    public void setAlarmId(long alarmId) {
        this.alarmId = alarmId;
    }

    @Override
    public HashMap<String, String> toAlarmData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("gid", String.valueOf(gid));
        return map;
    }
}
