package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.application.repository.AlarmTypeRepo;
import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.domain.AlarmBasic;
import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.domain.AlarmType;
import com.kesi.planit.alarm.infrastructure.AlarmBasicJpaEntity;
import com.kesi.planit.alarm.infrastructure.AlarmBasicJpaRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
@AllArgsConstructor
public class AlarmBasicService implements AlarmTypeData{
    private final AlarmTypeRepo<AlarmBasicJpaEntity> alarmTypeRepo;
    private final AlarmCRUDService alarmCRUDService;

    @Override
    public AlarmData getById(Long id) {
        AlarmBasicJpaEntity alarmBasicJpaEntity = alarmTypeRepo.findByAlarmId(id);
        Alarm alarm = alarmCRUDService.getById(alarmBasicJpaEntity.getAlarmId());
        return alarmTypeRepo.findById(id).toModel(alarm);
    }

    @Override
    public AlarmData getByAlarmId(Long alarmId) {
        Alarm alarm = alarmCRUDService.getById(alarmId);
        AlarmBasicJpaEntity alarmBasicJpaEntity = alarmTypeRepo.findByAlarmId(alarmId);
        return alarmBasicJpaEntity.toModel(alarm);
    }

    @Override
    public AlarmData save(AlarmData alarmData) {
        AlarmBasic alarmBasic = (AlarmBasic) alarmData;
        AlarmBasicJpaEntity alarmBasicJpaEntity = alarmTypeRepo.save(AlarmBasicJpaEntity.from(alarmBasic));
        return alarmBasicJpaEntity.toModel(alarmBasic.getAlarm());
    }

    @Override
    public void deleteById(Long id) {
        alarmTypeRepo.deleteById(id);
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.BASIC;
    }

}
