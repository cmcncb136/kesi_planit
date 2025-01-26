package com.kesi.planit.schedule.presentation;

import com.kesi.planit.schedule.application.ScheduleSecurityService;
import com.kesi.planit.schedule.presentation.dto.*;
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

    @GetMapping("/group")
    public ResponseEntity<List<GroupScheduleDto>> getGroupSchedule(HttpServletRequest request, String monthDate, Long gid) {
        return scheduleSecurityService.getGroupSchedulesAndMonth(monthDate, (String)request.getAttribute("uid"), gid);
    }

    @GetMapping("/other")
    public ResponseEntity<List<GroupUserScheduleDto>> getGroupUserSchedule(HttpServletRequest request, String monthDate, Long gid){
        return scheduleSecurityService.getGroupUserSchedulesAndMonth(monthDate, (String)request.getAttribute("uid"), gid);
    }

    @PostMapping("/group")
    public ResponseEntity<String> addGroupSchedule(@RequestBody RequestGroupScheduleDto groupScheduleDto, Long gid, HttpServletRequest request) {
        return scheduleSecurityService.addGroupSchedule(gid, groupScheduleDto, (String)request.getAttribute("uid"));
    }
}
