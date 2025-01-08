package com.kesi.planit.schedule.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleSecurityJpaRepo extends JpaRepository<ScheduleSecurityEntity, Long> {
    List<ScheduleSecurityEntity> findByScheduleId(Long scheduleId);

}
