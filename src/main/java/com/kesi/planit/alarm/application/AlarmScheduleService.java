package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.application.repository.AlarmTypeRepo;
import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.domain.AlarmType;
import com.kesi.planit.alarm.infrastructure.AlarmScheduleJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AlarmScheduleService implements AlarmTypeData{
    private final AlarmTypeRepo<AlarmScheduleJpaEntity> alarmTypeRepo;

    @Override
    public AlarmData getById(Long id) {
        return alarmTypeRepo.findById(id).toModel();
    }

    @Override
    public AlarmData getByAlarmId(Long alarmId) {
        return alarmTypeRepo.findByAlarmId(alarmId).toModel();
    }

    @Override
    public AlarmData save(AlarmData alarmData) {
        return alarmTypeRepo.save((AlarmScheduleJpaEntity) alarmData).toModel();
    }

    @Override
    public void deleteById(Long id) {
        alarmTypeRepo.deleteById(id);
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.SCHEDULE;
    }
}
