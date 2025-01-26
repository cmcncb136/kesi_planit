package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.group.Presentation.dto.GroupMemberDto;
import com.kesi.planit.schedule.domain.Schedule;
import lombok.Builder;

@Builder
public class GroupScheduleDto {
    public Long id;
    public GroupMemberDto makerName;
    public String colorId;
    public String title;
    public String description;
    public String startDate, endDate;
    public String startTime, endTime;

    public static GroupScheduleDto from(Schedule schedule) {
        return GroupScheduleDto.builder()
                .id(schedule.getId())
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
