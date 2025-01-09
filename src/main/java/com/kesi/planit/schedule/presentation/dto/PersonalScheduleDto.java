package com.kesi.planit.schedule.presentation.dto;

import com.google.cloud.firestore.collection.LLRBNode;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.user.domain.User;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PersonalScheduleDto {
    public String colorId;
    public String title;
    public String description;
    public String startDate, endDate;
    public String startTime, endTime;
    public Integer securityLevel;

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
