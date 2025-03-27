package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.group.Presentation.dto.GroupMemberDto;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.schedule.domain.SecurityLevel;
import lombok.Builder;

@Builder
public class GroupScheduleDto {
    public Long id;
    public GroupMemberDto makerName;
    public int colorValue;
    public String title;
    public String place;
    public String link;
    public String description;
    public String startDate, endDate;
    public String startTime, endTime;
    public SecurityLevel securityLevel;


    public static GroupScheduleDto from(ScheduleSecurity scheduleSecurity) {
        ScheduleSource schedule = scheduleSecurity.getSchedule();
        return GroupScheduleDto.builder()
                .id(schedule.getId())
                .makerName(GroupMemberDto.from(schedule.getMaker()))
                .colorValue(schedule.getColor().getRGB())
                .title(schedule.getTitle())
                .place(schedule.getPlace())
                .link(schedule.getLink())
                .description(schedule.getDescription())
                .startDate(schedule.getStartDate().toString())
                .endDate(schedule.getEndDate().toString())
                .startTime(schedule.getStartTime().toString())
                .endTime(schedule.getEndTime().toString())
                .securityLevel(scheduleSecurity.getSecurityLevel())
                .build();
    }
}
