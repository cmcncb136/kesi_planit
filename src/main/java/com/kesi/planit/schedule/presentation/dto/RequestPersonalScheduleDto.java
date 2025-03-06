package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public Schedule toModel(User maker) {

        return Schedule.builder()
                .color(new Color(colorValue, true))
                .title(title)
                .description(description)
                .link(link)
                .place(place)
                .startDate(LocalDate.parse(startDate))
                .endDate(LocalDate.parse(endDate))
                .startTime(LocalTime.parse(startTime))
                .endTime(LocalTime.parse(endTime))
                .sourceCalendar(maker.getMyCalendar())
                .maker(maker)
                .guestEditPermission(false)
                .build();
    }
}
