package com.kesi.planit.alarm.domain;

import lombok.Getter;

import java.util.HashMap;

@Getter
public abstract class AlarmData {
    private final Alarm alarm;

    public AlarmData(Alarm alarm) {
        if(alarm == null)
            throw new NullPointerException("alarm is null");

        this.alarm = alarm;
    }

    public abstract HashMap<String, String> toAlarmData();

}