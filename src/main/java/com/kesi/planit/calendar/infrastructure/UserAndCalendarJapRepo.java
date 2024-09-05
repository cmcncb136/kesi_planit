package com.kesi.planit.calendar.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAndCalendarJapRepo extends JpaRepository<UserAndCalendarJpaEntity, Long> {
    List<UserAndCalendarJpaEntity> findByUid(String uid);
    List<UserAndCalendarJpaEntity> findByCalendarId(Long calendarId);
}
