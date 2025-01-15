package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.domain.AlarmType;

public class AlarmBasicService implements AlarmTypeData{
    @Override
    public AlarmData getById(Long id) {
        return null;
    }

    @Override
    public AlarmData getByAlarmId(Long alarmId) {
        return null;
    }

    @Override
    public AlarmData save(AlarmData alarmData) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public AlarmType getAlarmType() {
        return null;
    }
}
