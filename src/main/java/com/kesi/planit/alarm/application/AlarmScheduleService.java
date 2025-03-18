package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.application.repository.AlarmTypeRepo;
import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.domain.AlarmSchedule;
import com.kesi.planit.alarm.domain.AlarmType;
import com.kesi.planit.alarm.infrastructure.AlarmScheduleJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AlarmScheduleService implements AlarmTypeData {
    private final AlarmTypeRepo<AlarmScheduleJpaEntity> alarmTypeRepo;
    private final AlarmCRUDService alarmCRUDService;

    @Override
    public AlarmData getById(Long id) {
        AlarmScheduleJpaEntity alarmScheduleJpaEntity = alarmTypeRepo.findById(id);
        Alarm alarm = alarmCRUDService.getById(alarmScheduleJpaEntity.getAlarmId());
        return alarmTypeRepo.findById(id).toModel(alarm);
    }

    @Override
    public AlarmData getByAlarmId(Long alarmId) {
        return alarmTypeRepo.findByAlarmId(alarmId).toModel(alarmCRUDService.getById(alarmId));
    }

    @Override
    public AlarmData save(AlarmData alarmData) {
        AlarmSchedule alarmSchedule = (AlarmSchedule) alarmData;
        return alarmTypeRepo.save(AlarmScheduleJpaEntity.from(alarmSchedule)).toModel(alarmSchedule.getAlarm());
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
