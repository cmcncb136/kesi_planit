package com.kesi.planit.schedule.presentation;

import com.kesi.planit.schedule.application.PersonalScheduleService;
import com.kesi.planit.schedule.application.ScheduleSecurityService;
import com.kesi.planit.schedule.presentation.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("schedule")
public class ScheduleController {
    private final PersonalScheduleService personalScheduleService;
    private final ScheduleSecurityService scheduleSecurityService;

    @GetMapping("")
    public ResponseEntity<List<PersonalScheduleDto>> getPersonalSchedulesWithMonth(@RequestParam("monthDate") String date, HttpServletRequest request) {
        return personalScheduleService.getPersonalSchedulesInMonth(date, (String) request.getAttribute("uid"));
    }

    @PostMapping("")
    public ResponseEntity<Long> addPersonalSchedule(@RequestBody RequestPersonalScheduleDto personalScheduleDto, HttpServletRequest request) {
        return personalScheduleService.addPersonalSchedule((String)request.getAttribute("uid"), personalScheduleDto);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deletePersonalSchedule(@RequestParam("scheduleId") Long scheduleId, HttpServletRequest request) {
        return personalScheduleService.removePersonalSchedule((String)request.getAttribute("uid"), scheduleId);
    }

    @PatchMapping("")
    public ResponseEntity<PersonalScheduleDto> updatePersonalSchedule(@RequestBody RequestPersonalUpdateScheduleDto requestPersonalUpdateScheduleDto, HttpServletRequest request) {
        return personalScheduleService.updatePersonalSchedule((String)request.getAttribute("uid"), requestPersonalUpdateScheduleDto);
    }

    @GetMapping("/group")
    public ResponseEntity<List<GroupScheduleDto>> getGroupSchedule(HttpServletRequest request, String monthDate, Long gid) {
        return scheduleSecurityService.getGroupSchedulesAndMonth(monthDate, (String)request.getAttribute("uid"), gid);
    }

    @GetMapping("/other")
    public ResponseEntity<List<GroupUserScheduleDto>> getGroupUserSchedule(HttpServletRequest request, String monthDate, Long gid){
        return scheduleSecurityService.getGroupUserSchedulesInMonth(monthDate, (String)request.getAttribute("uid"), gid);
    }

    @PostMapping("/group")
    public ResponseEntity<String> addGroupSchedule(@RequestBody RequestGroupScheduleDto groupScheduleDto, Long gid, HttpServletRequest request) {
        return scheduleSecurityService.addGroupSchedule(gid, groupScheduleDto, (String)request.getAttribute("uid"));
    }
}
