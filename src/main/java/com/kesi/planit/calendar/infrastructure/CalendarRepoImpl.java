package com.kesi.planit.calendar.infrastructure;

import com.kesi.planit.calendar.application.repository.CalendarRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CalendarRepoImpl implements CalendarRepo {
    private final CalendarJpaRepo calendarJpaRepo;

    @Override
    public CalendarJpaEntity findById(long id) {
        return calendarJpaRepo.findById(id).orElse(null);
    }

    @Override
    public CalendarJpaEntity save(CalendarJpaEntity calendar) {
        return calendarJpaRepo.save(calendar);
    }
}
