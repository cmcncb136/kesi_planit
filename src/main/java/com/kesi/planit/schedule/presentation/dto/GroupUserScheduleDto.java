package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.group.Presentation.dto.GroupMemberDto;
import com.kesi.planit.schedule.domain.ScheduleSource;
import lombok.Builder;

@Builder
public class GroupUserScheduleDto {
    public Long id;
    public GroupMemberDto scheduleOwner;
    public String title;
    public String link;
    public String place;
    public String description;
    public String startDate, endDate;
    public String startTime, endTime;

    public static GroupUserScheduleDto from(ScheduleSource schedule) {
        return GroupUserScheduleDto.builder()
                .id(schedule.getId())
                .scheduleOwner(GroupMemberDto.from(schedule.getMaker()))
                .title(schedule.getTitle())
                .link(schedule.getLink())
                .place(schedule.getPlace())
                .description(schedule.getDescription())
                .startDate(schedule.getStartDate().toString())
                .endDate(schedule.getEndDate().toString())
                .startTime(schedule.getStartTime().toString())
                .endTime(schedule.getEndTime().toString())
                .build();
    }
}
