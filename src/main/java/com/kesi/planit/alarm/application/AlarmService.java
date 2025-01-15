package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.application.repository.AlarmRepo;
import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.infrastructure.AlarmJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AlarmService {
    private final AlarmRepo alarmRepo;

    private Alarm getById(long id) {
        AlarmJpaEntity alarmJpaEntity = alarmRepo.findById(id);

        return null;
    }


}
