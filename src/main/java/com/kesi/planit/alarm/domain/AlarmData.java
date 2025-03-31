package com.kesi.planit.alarm.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Set;

@Getter
public abstract class AlarmData {
    private final Alarm alarm;
    private final Set<AlarmType> relationTypes; //특정 타입과 연관되어 있는지 확인

    public AlarmData(Alarm alarm, Set<AlarmType> relationTypes) {
        if(alarm == null)
            throw new NullPointerException("alarm is null");

        this.relationTypes = relationTypes;
        this.alarm = alarm;
    }

    public abstract HashMap<String, String> toAlarmData();
    public abstract boolean requireAction();

    public boolean relationType(AlarmType relationType) {
        return relationTypes.contains(relationType);
    }
}