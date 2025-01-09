package com.kesi.planit.schedule.infrastructure;

import com.kesi.planit.schedule.application.repository.ScheduleSecurityRepo;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
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
    public List<ScheduleSecurityEntity> findByUid(String uid) {
        return scheduleSecurityJpaRepo.findByUid(uid);
    }

    @Override
    public List<ScheduleSecurityEntity> findAll() {
        return scheduleSecurityJpaRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        scheduleSecurityJpaRepo.deleteById(id);
    }

    @Override
    public List<ScheduleSecurityEntity> findSchedulesUidAndWithinDateRange(String uid, LocalDate startDate, LocalDate endDate) {
        return scheduleSecurityJpaRepo.findSchedulesUidAndWithinDateRange(uid, startDate, endDate);
    }

    @Override
    public List<ScheduleSecurityEntity> findSchedulesWithinDateRange(LocalDate startDate, LocalDate endDate) {
        return scheduleSecurityJpaRepo.findSchedulesWithinDateRange(startDate, endDate);
    }


}
