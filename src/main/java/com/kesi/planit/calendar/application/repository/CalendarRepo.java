package com.kesi.planit.calendar.application.repository;

import com.kesi.planit.calendar.infrastructure.CalendarJpaEntity;
import org.springframework.stereotype.Component;

public interface CalendarRepo {
    CalendarJpaEntity findById(long id);
    CalendarJpaEntity save(CalendarJpaEntity calendar);
}
