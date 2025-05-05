package com.kesi.planit.schedule.application.repository;

import com.kesi.planit.schedule.infrastructure.ScheduleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepo {
    ScheduleJpaEntity save(ScheduleJpaEntity schedule);
    ScheduleJpaEntity findById(Long id);
    ScheduleJpaEntity update(ScheduleJpaEntity schedule);
    void deleteById(Long id);
    Page<ScheduleJpaEntity> findAll(Pageable pageable);
    List<ScheduleJpaEntity> findBySourceCalendarIdDateRange(Long sourceCalendarId, LocalDate startDate, LocalDate endDate);
}