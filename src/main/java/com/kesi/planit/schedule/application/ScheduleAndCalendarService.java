package com.kesi.planit.schedule.application;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.application.repository.ScheduleAndCalendarRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.domain.ScheduleAndCalendar;
import com.kesi.planit.schedule.infrastructure.ScheduleAndCalendarJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleAndCalendarService {
    private final ScheduleAndCalendarRepo scheduleAndCalendarRepo;

    public ScheduleAndCalendar findById(Long id) {
        return scheduleAndCalendarRepo.findById(id).tooModel();
    }

    public ScheduleAndCalendar save(ScheduleAndCalendar scheduleAndCalendar) {
        return scheduleAndCalendarRepo.save(ScheduleAndCalendarJpaEntity.from(scheduleAndCalendar)).tooModel();
    }

    public List<ScheduleAndCalendar> findBySchedule(Schedule schedule) {
        return getBySchedule(schedule.getId());
    }

    public List<ScheduleAndCalendar> getBySchedule(Long scheduleId) {
        return scheduleAndCalendarRepo.findByScheduleId(scheduleId)
                .stream().map(ScheduleAndCalendarJpaEntity::tooModel).toList();
    }



    public List<ScheduleAndCalendar> findByCalendar(Calendar calendar){
        return scheduleAndCalendarRepo.findByCalendarId(calendar.getId())
                .stream().map(ScheduleAndCalendarJpaEntity::tooModel).toList();
    }

}
