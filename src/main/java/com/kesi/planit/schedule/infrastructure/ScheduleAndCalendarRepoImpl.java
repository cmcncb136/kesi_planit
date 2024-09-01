package com.kesi.planit.schedule.infrastructure;

import com.kesi.planit.schedule.application.repository.ScheduleAndCalendarRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ScheduleAndCalendarRepoImpl implements ScheduleAndCalendarRepo {
    private final ScheduleAndCalendarJpaRepo scheduleAndCalendarRepo;

    @Override
    public ScheduleAndCalendarJpaEntity findById(Long id) {
        return scheduleAndCalendarRepo.findById(id).orElse(null);
    }

    @Override
    public ScheduleAndCalendarJpaEntity save(ScheduleAndCalendarJpaEntity scheduleAndCalendar) {
        return scheduleAndCalendarRepo.save(scheduleAndCalendar);
    }

    @Override
    public List<ScheduleAndCalendarJpaEntity> findByScheduleId(Long scheduleId) {
        return scheduleAndCalendarRepo.findByScheduleId(scheduleId);
    }

    @Override
    public List<ScheduleAndCalendarJpaEntity> findByCalendarId(Long calendarId) {
        return scheduleAndCalendarRepo.findByCalendarId(calendarId);
    }
}
