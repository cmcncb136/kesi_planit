package com.kesi.planit.calendar.application;

import com.kesi.planit.calendar.application.repository.UserAndCalendarRepo;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.calendar.domain.UserAndCalendar;
import com.kesi.planit.calendar.infrastructure.UserAndCalendarJpaEntity;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserAndCalendarService {
    private UserAndCalendarRepo userAndCalendarRepo;
    private UserService userService;
    private CalendarService calendarService;

    public UserAndCalendar getById(long id) {
        UserAndCalendarJpaEntity userAndCalendarJpaEntity
                = userAndCalendarRepo.findById(id);
        User user = userService.getUserById(userAndCalendarJpaEntity.getUid());
        Calendar calendar = calendarService.getById(userAndCalendarJpaEntity.getCalendarId());

        return  userAndCalendarJpaEntity.toModel(user, calendar);
    }

    public UserAndCalendar save(UserAndCalendar userAndCalendar) {
        return userAndCalendarRepo.save(UserAndCalendarJpaEntity.from(userAndCalendar)).toModel(
                userAndCalendar.getUser(), userAndCalendar.getCalendar()
        );
    }

}
