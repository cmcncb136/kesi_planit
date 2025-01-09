package com.kesi.planit.schedule.application.repository;

import com.kesi.planit.schedule.infrastructure.ScheduleSecurityEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public interface ScheduleSecurityRepo {
    ScheduleSecurityEntity save(ScheduleSecurityEntity schedule);
    ScheduleSecurityEntity findById(Long id);

    List<ScheduleSecurityEntity> findByScheduleId(Long scheduleId);
    List<ScheduleSecurityEntity> findByUid(String uid);

    List<ScheduleSecurityEntity> findAll();
    void deleteById(Long id);

    List<ScheduleSecurityEntity> findSchedulesUidAndWithinDateRange(String uid, LocalDate startDate, LocalDate endDate);
    List<ScheduleSecurityEntity> findSchedulesWithinDateRange(LocalDate startDate, LocalDate endDate);
}
