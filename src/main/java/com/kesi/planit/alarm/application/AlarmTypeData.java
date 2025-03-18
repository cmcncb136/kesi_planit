package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.domain.AlarmType;

public interface AlarmTypeData {
    AlarmData getById(Long id);
    AlarmData getByAlarmId(Long alarmId);
    AlarmData save(AlarmData alarmData);
    void deleteById(Long id);
    AlarmType getAlarmType();
}
