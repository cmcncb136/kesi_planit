package com.kesi.planit.schedule.presentation;

import com.kesi.planit.schedule.application.ScheduleSecurityService;
import com.kesi.planit.schedule.presentation.dto.PersonalScheduleDto;
import com.kesi.planit.schedule.presentation.dto.RequestPersonalScheduleDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("schedule")
public class ScheduleController {
    private final ScheduleSecurityService scheduleSecurityService;

    @GetMapping("")
    public ResponseEntity<List<PersonalScheduleDto>> getByCalendarId(@RequestParam("monthDate") String month, HttpServletRequest request) {
        return scheduleSecurityService.getPersonalSchedulesAndMonth(month, (String) request.getAttribute("uid"));
    }

    @PostMapping("")
    public ResponseEntity<String> addPersonalSchedule(@RequestBody RequestPersonalScheduleDto personalScheduleDto, HttpServletRequest request) {
        return scheduleSecurityService.addPersonalSchedule((String)request.getAttribute("uid"), personalScheduleDto);
    }

    @GetMapping("/other")
    public List<PersonalScheduleDto> getByPersonalIdAndMonth(HttpServletRequest request, String monthDate, Long calendarId) {
        return null;
    }

    @PostMapping("/group")
    public ResponseEntity<String> addGroupSchedule(){
        return null;
    }

    @GetMapping("/group")
    public List<PersonalScheduleDto> getGroupSchedule(){
        return null;
    }
}
