package com.kesi.planit.schedule.application;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.application.repository.ScheduleRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.infrastructure.ScheduleJpaEntity;
import com.kesi.planit.schedule.presentation.dto.PersonalScheduleDto;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;
    private final CalendarService calendarService;
    private final UserService userService;


    public Schedule getById(Long id) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleRepo.findById(id);
        User maker = userService.getUserById(scheduleJpaEntity.getMakerUid());
        Calendar calendar = calendarService.getById(scheduleJpaEntity.getSourceCalendarId());


        //Schedule 객체를 도메인 객체로 매핑하기 위해 Calendars 가 필요함.
        return scheduleJpaEntity.toModel(maker, calendar);
    }


    public Schedule save(Schedule schedule) {
        return  scheduleRepo.save(ScheduleJpaEntity.from(schedule)).toModel(
                schedule.getMaker(), schedule.getSourceCalendar());
    }

    //유저와 연결된 스케줄 기간 조회
    public List<Schedule> getBySourceCalendarIdAndDateRange(Long sourceCalendarId, LocalDate startDate, LocalDate endDate) {
        Calendar calendar = calendarService.getById(sourceCalendarId);

        return scheduleRepo.findBySourceCalendarIdDateRange(sourceCalendarId, startDate, endDate).stream().map(
                scheduleJpaEntity ->
                        scheduleJpaEntity.toModel(userService.getUserById(scheduleJpaEntity.getMakerUid()), calendar))
                .toList();
    }

    //유저와 연결된 스케줄 월별 조회
    public List<Schedule> getBySourceCalendarIdAndMonth(Long sourceCalendarId, LocalDate month) {
        LocalDate startDate = LocalDate.of(month.getYear(), month.getMonthValue(), 1);
        LocalDate endDate = LocalDate.of(month.getYear(), month.getMonthValue(), month.lengthOfMonth());

        return getBySourceCalendarIdAndDateRange(sourceCalendarId, startDate, endDate);
    }

    //캘린더 아이디로 스케줄을 조회
    //너무 많은 양이 조회될 수 있음으로 나중에 성능개선에 대해서 생각해 볼 필요가 있음
    public List<PersonalScheduleDto> getByCalendarId(Long calendarId) {

        //Todo. securityLevel에 의해서
        return null; //scheduleAndCalendarJpaEntities.stream().map(it -> getById(it.getScheduleId())).toList();
    }
}
