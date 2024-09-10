package com.kesi.planit.schedule.presentation;

import com.kesi.planit.schedule.application.ScheduleService;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.presentation.dto.ScheduleDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private ScheduleService scheduleService;

    @GetMapping("/")
    public List<ScheduleDto> getByCalendarId(@RequestParam("calendarId")Long calendarId) {
        return scheduleService.getByCalendarId(calendarId).stream().map(it -> ScheduleDto.from(it)).toList();
    }

    


}
