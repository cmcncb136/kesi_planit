package com.kesi.planit.schedule.application;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.schedule.application.repository.ScheduleAndCalendarRepo;
import com.kesi.planit.schedule.application.repository.ScheduleRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.infrastructure.ScheduleAndCalendarJpaEntity;
import com.kesi.planit.schedule.infrastructure.ScheduleJpaEntity;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;
    //순환 참조 주의
    //Calendar가 List<Schedule>를 가지 않게하는 이유는 Calendar
    //조회시 너무 많은 Schedule select 되지 않기 위함이다.
    private final CalendarService calendarService;
    private final UserService userService;
    private final ScheduleAndCalendarRepo scheduleAndCalendarRepo;

    //Todo. 스케줄을 추가할 때 동작할 함수 구현

    ///의존성
    public Schedule getById(Long id) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleRepo.findById(id);

        User maker = userService.getUserById(scheduleJpaEntity.getMakerUid());

        List<ScheduleAndCalendarJpaEntity> scheduleAndCalendars = scheduleAndCalendarRepo.findByScheduleId(id);

        //Schedule 객체를 도메인 객체로 매핑하기 위해 Calendars 가 필요함.
        return scheduleRepo.findById(id).toModel(maker,
                scheduleAndCalendars.stream().map(
                        it -> calendarService.getById(it.getCalendarId()))
                        .toList()
                );
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
