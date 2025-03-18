package com.kesi.planit.schedule.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleJpaRepo extends JpaRepository<ScheduleJpaEntity, Long> {
    @Query("SELECT s FROM ScheduleJpaEntity  s WHERE s.sourceCalendarId = :sourceCalendarId " +
            "AND s.startDate <= :endDate AND s.endDate >= :startDate")
    List<ScheduleJpaEntity> findBySourceCalendarIdDateRage(
            @Param("sourceCalendarId") Long sourceCalendarId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
