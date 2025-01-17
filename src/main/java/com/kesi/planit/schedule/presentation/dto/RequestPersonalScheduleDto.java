package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.user.domain.User;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RequestPersonalScheduleDto {
    public String colorId;
    public String title;
    public String description;
    public String startDate, endDate;
    public String startTime, endTime;
    public SecurityLevel securityLevel;

    public Schedule toModel(User maker) {

        return Schedule.builder()
                .color(Color.getColor(colorId))
                .title(title)
                .description(description)
                .startDate(LocalDate.parse(startDate))
                .endDate(LocalDate.parse(endDate))
                .startTime(LocalDateTime.parse(startTime))
                .endTime(LocalDateTime.parse(endTime))
                .maker(maker)
                .guestEditPermission(false)
                .build();
    }
}
