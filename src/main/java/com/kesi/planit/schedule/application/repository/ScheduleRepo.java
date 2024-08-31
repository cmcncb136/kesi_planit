package com.kesi.planit.schedule.application.repository;

import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.infrastructure.ScheduleJpaEntity;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepo {
    ScheduleJpaEntity save(ScheduleJpaEntity schedule);
    ScheduleJpaEntity findById(Long id);
    ScheduleJpaEntity update(ScheduleJpaEntity schedule);
    void delete(ScheduleJpaEntity schedule);
    List<ScheduleJpaEntity> findAll();
    List<ScheduleJpaEntity> findByCalendarId(Long calendarId);
    List<ScheduleJpaEntity> findByCalendarIdAndDateBetween(Long calendarId, LocalDate start, LocalDate end);

}
