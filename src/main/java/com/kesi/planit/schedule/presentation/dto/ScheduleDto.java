package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.schedule.domain.Schedule;
import lombok.Builder;

import java.awt.*;

@Builder
public class ScheduleDto {
    public String makerName;
    public String colorId;
    public String title;
    public String description;
    public String startDate, endDate;
    public String startTime, endTime;

    public static ScheduleDto from(Schedule schedule) {
        return ScheduleDto.builder()
                .makerName(schedule.getMaker().getNickname())
                .colorId(schedule.getColor().toString())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .startDate(schedule.getStartDate().toString())
                .endDate(schedule.getEndDate().toString())
                .startTime(schedule.getStartTime().toString())
                .endTime(schedule.getEndTime().toString())
                .build();
    }
}
