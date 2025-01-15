package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.application.repository.AlarmTypeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AlarmScheduleRepoImpl implements AlarmTypeRepo<AlarmScheduleJpaEntity> {
    private final AlarmTypeJpaRepo<AlarmScheduleJpaEntity> alarmTypeJpaRepo;

    @Override
    public AlarmScheduleJpaEntity findById(Long id) {
        return alarmTypeJpaRepo.findById(id).orElse(null);
    }

    @Override
    public AlarmScheduleJpaEntity findByAlarmId(Long alarmId) {
        return alarmTypeJpaRepo.findByAlarmId(alarmId);
    }

    @Override
    public AlarmScheduleJpaEntity save(AlarmScheduleJpaEntity alarmScheduleJpaEntity) {
        return alarmTypeJpaRepo.save(alarmScheduleJpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        alarmTypeJpaRepo.deleteById(id);
    }
}
