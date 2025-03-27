package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class RequestGroupScheduleDto {
    public int colorValue;
    public String title;
    public String description;
    public String link;
    public String place;
    public String startDate, endDate;
    public String startTime, endTime;
    public Boolean guestEditPermission;

    public ScheduleSource toModel(User maker, Calendar groupCalendar) {
        return ScheduleSource.builder()
                .color(new Color(colorValue, true))
                .title(title)
                .description(description)
                .link(link)
                .place(place)
                .startDate(LocalDate.parse(startDate))
                .endDate(LocalDate.parse(endDate))
                .startTime(LocalTime.parse(startTime))
                .endTime(LocalTime.parse(endTime))
                .sourceCalendar(groupCalendar)
                .maker(maker)
                .guestEditPermission(guestEditPermission)
                .build();
    }
}
