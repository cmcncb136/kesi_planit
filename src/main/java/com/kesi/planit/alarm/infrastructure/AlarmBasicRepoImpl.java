package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.application.repository.AlarmTypeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AlarmBasicRepoImpl implements AlarmTypeRepo<AlarmBasicJpaEntity> {
    private final AlarmBasicJpaRepo alarmBasicJpaRepo;

    @Override
    public AlarmBasicJpaEntity findById(Long id) {
        return alarmBasicJpaRepo.findById(id).orElse(null);
    }
 
    @Override
    public AlarmBasicJpaEntity findByAlarmId(Long alarmId) {
        return alarmBasicJpaRepo.findByAlarmId(alarmId);
    }

    @Override
    public AlarmBasicJpaEntity save(AlarmBasicJpaEntity alarmBasicJpaEntity) {
        return alarmBasicJpaRepo.save(alarmBasicJpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        alarmBasicJpaRepo.deleteById(id);
    }
}
