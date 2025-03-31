package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.schedule.domain.SecurityLevel;

public class RequestGroupScheduleSecurityDto {
    public Long scheduleId;
    public SecurityLevel securityLevel;

    public ScheduleSecurity toModel(ScheduleSource source) {
        return ScheduleSecurity.builder()
                .securityLevel(securityLevel)
                .schedule(source)
                .build();
    }
}
