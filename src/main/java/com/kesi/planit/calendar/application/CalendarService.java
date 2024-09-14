package com.kesi.planit.calendar.application;

import com.kesi.planit.calendar.application.repository.CalendarRepo;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.calendar.infrastructure.CalendarJpaEntity;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CalendarService {
    private final CalendarRepo calendarRepo;

    public Calendar getById(long id) {
        return calendarRepo.findById(id).toModel();
    }

    public Calendar save(Calendar calendar) {
        return calendarRepo.save(CalendarJpaEntity.from(calendar)).toModel();
    }
}
