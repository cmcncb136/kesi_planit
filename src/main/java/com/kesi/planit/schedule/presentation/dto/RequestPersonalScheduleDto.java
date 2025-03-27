package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class RequestPersonalScheduleDto {
    public int colorValue;
    public String title;
    public String description;
    public String link;
    public String place;
    public String startDate, endDate;
    public String startTime, endTime;
    public SecurityLevel securityLevel;


    public ScheduleSecurity toModel(User user) {
        ScheduleSource scheduleSource = ScheduleSource.builder()
                .color(new Color(colorValue, true))
                .title(title)
                .description(description)
                .link(link)
                .place(place)
                .startDate(LocalDate.parse(startDate))
                .endDate(LocalDate.parse(endDate))
                .startTime(LocalTime.parse(startTime))
                .endTime(LocalTime.parse(endTime))
                .sourceCalendar(user.getMyCalendar())
                .maker(user)
                .guestEditPermission(false)
                .build();

        return ScheduleSecurity.builder()
                .user(user)
                .securityLevel(securityLevel)
                .schedule(scheduleSource)
                .build();
    }
}
