package com.kesi.planit.schedule.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleJpaRepo extends JpaRepository<ScheduleJpaEntity, Long> {
    List<ScheduleJpaEntity> findByCalendarId(Long calendarId);
    List<ScheduleJpaEntity> findByCalendarIdAndDateBetween(Long calendarId, LocalDate start, LocalDate end);
}
