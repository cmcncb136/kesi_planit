package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.application.repository.AlarmRepo;
import com.kesi.planit.alarm.application.repository.AlarmTypeRepo;
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

    @Override
    public AlarmData getById(Long id) {
        return alarmTypeRepo.findByAlarmId(id).toModel();
    }

    @Override
    public AlarmData getByAlarmId(Long alarmId) {
        return alarmTypeRepo.findByAlarmId(alarmId).toModel();
    }

    @Override
    public AlarmData save(AlarmData alarmData) {
        AlarmGroup alarmGroup = (AlarmGroup) alarmData;
        return alarmTypeRepo.save(AlarmGroupJapEntity.from(alarmGroup)).toModel();
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
