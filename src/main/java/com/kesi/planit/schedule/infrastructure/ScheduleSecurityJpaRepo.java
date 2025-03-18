package com.kesi.planit.schedule.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleSecurityJpaRepo extends JpaRepository<ScheduleSecurityEntity, Long> {
    List<ScheduleSecurityEntity> findByScheduleId(Long scheduleId);
    List<ScheduleSecurityEntity> findByUid(String uid);
    ScheduleSecurityEntity findByUidAndScheduleId(String uid, Long scheduleId);
    void deleteByScheduleId(Long scheduleId);

    //빠른 탐색을 위해서 쿼리문으로 접근
    @Query("SELECT s FROM ScheduleSecurityEntity s JOIN ScheduleJpaEntity sc ON s.scheduleId = sc.id " +
            "WHERE s.uid = :uid " +
            "AND sc.startDate <= :endDate AND sc.endDate >= :startDate")
    List<ScheduleSecurityEntity> findSchedulesUidAndWithInDateRange(@Param("uid") String uid,
                                                                    @Param("startDate") LocalDate startDate,
                                                                    @Param("endDate") LocalDate endDate);


    @Query("SELECT s FROM ScheduleSecurityEntity s JOIN ScheduleJpaEntity sc ON s.scheduleId = sc.id " +
            "AND sc.startDate <= :endDate AND sc.endDate >= :startDate")
    List<ScheduleSecurityEntity> findSchedulesWithInDateRange(@Param("startDate") LocalDate startDate,
                                                              @Param("endDate") LocalDate endDate);

}
