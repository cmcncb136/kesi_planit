package com.kesi.planit.schedule.infrastructure;

import com.kesi.planit.schedule.application.repository.ScheduleRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class ScheduleRepoImpl implements ScheduleRepo{
    private final ScheduleJpaRepo scheduleJpaRepo;

    @Override
    public ScheduleJpaEntity save(ScheduleJpaEntity schedule) {
        return scheduleJpaRepo.save(schedule);
    }

    @Override
    public ScheduleJpaEntity findById(Long id) {
        return scheduleJpaRepo.findById(id).orElse(null);
    }

    @Override
    public ScheduleJpaEntity update(ScheduleJpaEntity schedule) {
        return scheduleJpaRepo.save(schedule);
    }

    @Override
    public void deleteById(Long id) {
        scheduleJpaRepo.deleteById(id);
    }


    @Override
    public List<ScheduleJpaEntity> findAll() {
        return scheduleJpaRepo.findAll();
    }

    @Override
    public List<ScheduleJpaEntity> findBySourceCalendarIdDateRange(Long sourceCalendarId, LocalDate startDate, LocalDate endDate) {
        return scheduleJpaRepo.findBySourceCalendarIdDateRage(sourceCalendarId, startDate, endDate);
    }
}
