package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.domain.AlarmType;

public interface AlarmTypeData {
    AlarmData getById(Long id);
    AlarmData getByAlarmId(Long alarmId);
    //Todo. save() 기능이 있어도 되는지 좀 고민되네
    AlarmData save(AlarmData alarmData);
    void deleteById(Long id);
    AlarmType getAlarmType();
}
