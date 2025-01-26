package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class RequestGroupScheduleDto {
    public String colorId;
    public String title;
    public String description;
    public String startDate, endDate;
    public String startTime, endTime;
    public Boolean guestEditPermission;

    public Schedule toModel(User maker, Calendar groupCalendar) {
        return Schedule.builder()
                .color(Color.getColor(colorId))
                .title(title)
                .description(description)
                .startDate(LocalDate.parse(startDate))
                .endDate(LocalDate.parse(endDate))
                .startTime(LocalDateTime.parse(startTime))
                .endTime(LocalDateTime.parse(endTime))
                .sourceCalendar(groupCalendar)
                .maker(maker)
                .guestEditPermission(guestEditPermission)
                .build();
    }
}
