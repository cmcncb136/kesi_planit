package com.kesi.planit.alarm.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

@Getter
@Builder
public class AlarmSchedule implements AlarmData{
    private long id;
    private long gid;
    private long scheduleId;
    private long alarmId;


    @Override
    public HashMap<String, String> toAlarmData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("gid", String.valueOf(gid));
        map.put("scheduleId", String.valueOf(scheduleId));

        return map;
    }
}
