package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.application.repository.AlarmRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class AlarmRepoImpl implements AlarmRepo {
    private final AlarmJpaRepo alarmJpaRepo;

    @Override
    public AlarmJpaEntity findById(long id) {
        return alarmJpaRepo.findById(id).orElse(null);
    }

    @Override
    public AlarmJpaEntity save(AlarmJpaEntity alarm) {
        return alarmJpaRepo.save(alarm);
    }

    @Override
    public List<AlarmJpaEntity> findByUid(String uid) {
        return alarmJpaRepo.findByUid(uid);
    }

    @Override
    public List<AlarmJpaEntity> findAll() {
        return alarmJpaRepo.findAll();
    }

    @Override
    public void deleteById(long id) {
        alarmJpaRepo.deleteById(id);
    }
}
