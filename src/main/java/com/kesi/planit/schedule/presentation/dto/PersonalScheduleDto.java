package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.group.Presentation.dto.GroupMemberDto;
import com.kesi.planit.schedule.domain.Schedule;
import lombok.Builder;

@Builder
public class PersonalScheduleDto {
    public Long id;
    public GroupMemberDto makerName;
    public int colorValue;
    public String title;
    public String link;
    public String place;
    public String description;
    public String startDate, endDate;
    public String startTime, endTime;

    public static PersonalScheduleDto from(Schedule schedule) {
        return PersonalScheduleDto.builder()
                .id(schedule.getId())
                .makerName(GroupMemberDto.from(schedule.getMaker()))
                .colorValue(schedule.getColor().getRGB())
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
