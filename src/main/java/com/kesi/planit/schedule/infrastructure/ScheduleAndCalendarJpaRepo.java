package com.kesi.planit.schedule.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleAndCalendarJpaRepo extends JpaRepository<ScheduleAndCalendarJpaEntity, Long> {
    List<ScheduleAndCalendarJpaEntity> findByScheduleId(Long scheduleId);
    List<ScheduleAndCalendarJpaEntity> findByCalendarId(Long calendarId);
}
