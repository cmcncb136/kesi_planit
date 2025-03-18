package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.application.repository.AlarmRepo;
import com.kesi.planit.alarm.application.repository.AlarmTypeRepo;
import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.domain.AlarmGroup;
import com.kesi.planit.alarm.domain.AlarmType;
import com.kesi.planit.alarm.infrastructure.AlarmGroupJapEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AlarmGroupService implements AlarmTypeData{
    private final AlarmTypeRepo<AlarmGroupJapEntity> alarmTypeRepo;
    private final AlarmCRUDService alarmCRUDService;


    @Override
    public AlarmData getById(Long id) {
        AlarmGroupJapEntity alarmGroupJapEntity = alarmTypeRepo.findByAlarmId(id);
        Alarm alarm = alarmCRUDService.getById(id);

        return alarmGroupJapEntity.toModel(alarm);
    }

    @Override
    public AlarmData getByAlarmId(Long alarmId) {
        return alarmTypeRepo.findByAlarmId(alarmId).toModel(alarmCRUDService.getById(alarmId));
    }

    @Override
    public AlarmData save(AlarmData alarmData) {
        AlarmGroup alarmGroup = (AlarmGroup) alarmData;
        return alarmTypeRepo.save(AlarmGroupJapEntity.from(alarmGroup)).toModel(alarmGroup.getAlarm());
    }

    @Override
    public void deleteById(Long id) {
        alarmTypeRepo.deleteById(id);
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.GROUP;
    }
}
