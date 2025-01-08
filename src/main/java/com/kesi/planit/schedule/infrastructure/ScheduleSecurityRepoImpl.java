package com.kesi.planit.schedule.infrastructure;

import com.kesi.planit.schedule.application.repository.ScheduleSecurityRepo;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ScheduleSecurityRepoImpl implements ScheduleSecurityRepo {
    private final ScheduleSecurityJpaRepo scheduleSecurityJpaRepo;

    @Override
    public ScheduleSecurityEntity save(ScheduleSecurityEntity schedule) {
        return scheduleSecurityJpaRepo.save(schedule);
    }

    @Override
    public ScheduleSecurityEntity findById(Long id) {
        return scheduleSecurityJpaRepo.findById(id).orElse(null);
    }

    @Override
    public List<ScheduleSecurityEntity> findByScheduleId(Long scheduleId) {
        return scheduleSecurityJpaRepo.findByScheduleId(scheduleId);
    }

    @Override
    public List<ScheduleSecurityEntity> findAll() {
        return scheduleSecurityJpaRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        scheduleSecurityJpaRepo.deleteById(id);
    }
}
