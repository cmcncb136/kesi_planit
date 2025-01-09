package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.group.Presentation.dto.GroupMemberDto;
import com.kesi.planit.schedule.domain.Schedule;
import lombok.Builder;

import java.awt.*;

@Builder
public class ScheduleDto {
    public GroupMemberDto makerName;
    public String colorId;
    public String title;
    public String description;
    public String startDate, endDate;
    public String startTime, endTime;

    public static ScheduleDto from(Schedule schedule) {
        return ScheduleDto.builder()
                .makerName(GroupMemberDto.from(schedule.getMaker()))
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
