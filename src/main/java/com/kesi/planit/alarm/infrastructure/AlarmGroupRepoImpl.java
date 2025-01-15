package com.kesi.planit.alarm.infrastructure;
import com.kesi.planit.alarm.application.repository.AlarmTypeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class AlarmGroupRepoImpl implements AlarmTypeRepo<AlarmGroupJapEntity> {
   private final AlarmTypeJpaRepo<AlarmGroupJapEntity> alarmTypeJpaRepo;

    @Override
    public AlarmGroupJapEntity findById(Long id) {
        return alarmTypeJpaRepo.findById(id).orElse(null);
    }

    @Override
    public AlarmGroupJapEntity findByAlarmId(Long alarmId) {
        return alarmTypeJpaRepo.findByAlarmId(alarmId);
    }

    @Override
    public AlarmGroupJapEntity save(AlarmGroupJapEntity alarmGroupJapEntity) {
        return alarmGroupJapEntity;
    }

    @Override
    public void deleteById(Long id) {
        alarmTypeJpaRepo.deleteById(id);
    }
}
