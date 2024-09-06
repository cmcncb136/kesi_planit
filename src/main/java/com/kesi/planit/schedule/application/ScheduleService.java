package com.kesi.planit.schedule.application;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.application.repository.ScheduleAndCalendarRepo;
import com.kesi.planit.schedule.application.repository.ScheduleRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.domain.ScheduleAndCalendar;
import com.kesi.planit.schedule.infrastructure.ScheduleAndCalendarJpaEntity;
import com.kesi.planit.schedule.infrastructure.ScheduleJpaEntity;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;
    private final ScheduleAndCalendarService scheduleAndCalendarService;
    //순환 참조 주의
    //Calendar가 List<Schedule>를 가지 않게하는 이유는 Calendar
    //조회시 너무 많은 Schedule select 되지 않기 위함이다.
    private final CalendarService calendarService;
    private final UserService userService;
    private final ScheduleAndCalendarRepo scheduleAndCalendarRepo;


    ///의존성
    public Schedule getById(Long id) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleRepo.findById(id);

        User maker = userService.getUserById(scheduleJpaEntity.getMakerUid());

        List<ScheduleAndCalendar> scheduleAndCalendars = scheduleAndCalendarService.getBySchedule(id);

        List<Calendar> calendars = new ArrayList<>();
        for (ScheduleAndCalendar scheduleAndCalendar : scheduleAndCalendars)
            calendars.add(calendarService.getById(scheduleAndCalendar.getCalendarId()));

        return scheduleRepo.findById(id).toModel(maker, calendars);
    }

    public Schedule save(Schedule schedule) {
        return  scheduleRepo.save(ScheduleJpaEntity.from(schedule)).toModel(
                schedule.getMaker(), schedule.getCalendars()
        );
    }


    public List<Schedule> getByCalendarId(Long calendarId) {
        List<ScheduleAndCalendarJpaEntity> scheduleAndCalendarJpaEntities
                = scheduleAndCalendarRepo.findByCalendarId(calendarId);

        return scheduleAndCalendarJpaEntities.stream().map(it -> getById(it.getScheduleId())).toList();
    }


}
