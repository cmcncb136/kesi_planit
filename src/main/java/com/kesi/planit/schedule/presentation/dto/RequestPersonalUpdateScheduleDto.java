package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.schedule.application.ScheduleSecurityService;
import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.user.domain.User;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class RequestPersonalUpdateScheduleDto {
    public Long id;
    public int colorValue;
    public String title;
    public String description;
    public String link;
    public String place;
    public String startDate, endDate;
    public String startTime, endTime;
    public SecurityLevel securityLevel;


    public ScheduleSecurity toModel(User user, long securityScheduleId) {
        ScheduleSource scheduleSource = ScheduleSource.builder()
                .id(id)
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
                .id(securityScheduleId)
                .schedule(scheduleSource)
                .securityLevel(securityLevel)
                .user(user)
                .build();
    }
}
