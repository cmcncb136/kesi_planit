package com.kesi.planit.schedule.application;

import com.kesi.planit.schedule.application.repository.ScheduleSecurityRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.infrastructure.ScheduleSecurityEntity;
import com.kesi.planit.schedule.infrastructure.ScheduleSecurityJpaRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleSecurityService {
    private final ScheduleService scheduleService;
    private final ScheduleSecurityRepo scheduleSecurityRepo;

    public List<Schedule> getScheduleMonthByUid(LocalDate monthDate, String uid) {
        LocalDate startDate = LocalDate.of(monthDate.getYear(), monthDate.getMonthValue(), 1); //해당 월에 첫 날
        LocalDate endDate = LocalDate.of(monthDate.getYear(), monthDate.getMonthValue(), monthDate.lengthOfMonth()); //해당 월에 마지막 날

        List<ScheduleSecurityEntity> scheduleSecurityEntities
                = scheduleSecurityRepo.findSchedulesUidAndWithinDateRange(uid, startDate, endDate);


        List<Schedule> scheduleList = scheduleSecurityEntities.stream().map(it ->
                scheduleService.getById(it.getScheduleId())).toList();

        return scheduleList;
    }
}
