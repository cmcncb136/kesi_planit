package com.kesi.planit.schedule.application.repository;

import com.kesi.planit.schedule.infrastructure.ScheduleAndCalendarJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ScheduleAndCalendarRepo {
    ScheduleAndCalendarJpaEntity findById(Long id);
    ScheduleAndCalendarJpaEntity save(ScheduleAndCalendarJpaEntity scheduleAndCalendar);
    List<ScheduleAndCalendarJpaEntity> findByScheduleId(Long scheduleId);
    List<ScheduleAndCalendarJpaEntity> findByCalendarId(Long calendarId);
}
