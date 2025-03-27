package com.kesi.planit.schedule.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleSourceService scheduleSourceService;
    private final ScheduleSecurityService scheduleSecurityService;

}
