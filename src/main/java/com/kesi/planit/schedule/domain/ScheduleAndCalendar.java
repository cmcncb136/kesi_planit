package com.kesi.planit.schedule.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleAndCalendar {
    private Long id;
    private Long calendarId;
    private Long scheduleId;

}
