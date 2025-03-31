package com.kesi.planit.alarm.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Set;

@Getter
public class AlarmSchedule extends AlarmData implements GroupRelated{
    private final Long id;
    private final Long gid;
    private final Long scheduleId;

    @Builder
    public AlarmSchedule(Long id, Long gid, Long scheduleId, Alarm alarm) {
        super(alarm, Set.of(AlarmType.GROUP, AlarmType.SCHEDULE));
        this.id = id;
        this.gid = gid;
        this.scheduleId = scheduleId;
    }

    @Override
    public HashMap<String, String> toAlarmData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("gid", String.valueOf(gid));
        map.put("scheduleId", String.valueOf(scheduleId));
        map.put("type", AlarmType.SCHEDULE.name());

        return map;
    }

    @Override
    public boolean requireAction() {
        return true;
    }
}
