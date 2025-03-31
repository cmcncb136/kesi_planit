package com.kesi.planit.alarm.domain;

import com.kesi.planit.group.domain.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.HashMap;
import java.util.Set;

@Getter
public class AlarmGroup extends AlarmData implements GroupRelated {
    private final Long id;
    private final Long gid;

    @Builder
    public AlarmGroup(Long id, Long gid, Alarm alarm) {
        super(alarm, Set.of(AlarmType.GROUP));
        this.id = id;
        this.gid = gid;
    }


    @Override
    public HashMap<String, String> toAlarmData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("gid", String.valueOf(gid));
        map.put("type", AlarmType.GROUP.name());
        return map;
    }

    @Override
    public boolean requireAction() {
        return false;
    }
}
