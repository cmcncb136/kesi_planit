package com.kesi.planit.schedule.presentation;

import com.kesi.planit.schedule.application.GroupScheduleService;
import com.kesi.planit.schedule.presentation.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("schedule/group")
public class GroupScheduleController {
    private final GroupScheduleService groupScheduleService;

    @GetMapping("")
    public ResponseEntity<List<GroupScheduleDto>> getGroupSchedulesInMonth(HttpServletRequest request, String monthDate) {
        return groupScheduleService.getAllGroupSchedulesInMonth(monthDate, (String)request.getAttribute("uid"));
    }

//    @GetMapping("/other")
//    public ResponseEntity<List<GroupUserScheduleDto>> getGroupUserSchedule(HttpServletRequest request, String monthDate, Long gid){
//        return scheduleSecurityService.getGroupUserSchedulesInMonth(monthDate, (String)request.getAttribute("uid"), gid);
//    }
//
//    @PostMapping("")
//    public ResponseEntity<String> addGroupSchedule(@RequestBody RequestGroupScheduleDto groupScheduleDto, Long gid, HttpServletRequest request) {
//        return scheduleSecurityService.addGroupSchedule(gid, groupScheduleDto, (String)request.getAttribute("uid"));
//    }

}
