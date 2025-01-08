package com.kesi.planit.schedule.application.repository;

import com.kesi.planit.schedule.infrastructure.ScheduleSecurityEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ScheduleSecurityRepo {
    ScheduleSecurityEntity save(ScheduleSecurityEntity schedule);
    ScheduleSecurityEntity findById(Long id);

    List<ScheduleSecurityEntity> findByScheduleId(Long scheduleId);

    List<ScheduleSecurityEntity> findAll();
    void deleteById(Long id);
}
