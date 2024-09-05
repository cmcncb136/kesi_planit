package com.kesi.planit.calendar.application.repository;

import com.kesi.planit.calendar.infrastructure.UserAndCalendarJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserAndCalendarRepo {
    UserAndCalendarJpaEntity findById(Long id);
    List<UserAndCalendarJpaEntity> findByUserId(String uid);
    List<UserAndCalendarJpaEntity> findByCalendarId(Long calendarId);
    UserAndCalendarJpaEntity save(UserAndCalendarJpaEntity userAndCalendarJpaEntity);
}
