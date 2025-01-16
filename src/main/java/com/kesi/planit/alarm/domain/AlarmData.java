package com.kesi.planit.alarm.domain;

import java.util.HashMap;

public interface AlarmData {
    void setAlarmId(long alarmId);
    HashMap<String, String> toAlarmData();
}