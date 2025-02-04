package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.domain.AlarmBasic;
import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.domain.AlarmType;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AlarmBasicService implements AlarmTypeData{
    @Override
    public AlarmData getById(Long id) {
        return createFakeAlarmData();
    }

    @Override
    public AlarmData getByAlarmId(Long alarmId) {
        return createFakeAlarmData();
    }

    @Override
    public AlarmData save(AlarmData alarmData) {
        return createFakeAlarmData();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.BASIC;
    }


    private AlarmData createFakeAlarmData() {
        return AlarmBasic.builder().build();
    }
}
