package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.group.Presentation.dto.GroupMemberDto;
import com.kesi.planit.schedule.domain.FilteredSchedule;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.user.domain.User;
import lombok.Builder;

@Builder
public class GroupUserScheduleDto {
    public Long id;
    public GroupMemberDto sourceUser; //참조한 유저에 정보
    public String title;
    public String link;
    public String place;
    public String description;
    public String startDate, endDate;
    public String startTime, endTime;

    public static GroupUserScheduleDto from(FilteredSchedule filteredSchedule) {
        return GroupUserScheduleDto.builder()
                .id(filteredSchedule.getId())
                .sourceUser(GroupMemberDto.from(filteredSchedule.getSourceUser()))
                .title(filteredSchedule.getTitle())
                .link(filteredSchedule.getLink())
                .place(filteredSchedule.getPlace())
                .description(filteredSchedule.getDescription())
                .startDate(filteredSchedule.getStartDate().toString())
                .endDate(filteredSchedule.getEndDate().toString())
                .startTime(filteredSchedule.getStartTime().toString())
                .endTime(filteredSchedule.getEndTime().toString())
                .build();
    }

    public static GroupUserScheduleDto from(ScheduleSource schedule, User user) {
        return GroupUserScheduleDto.builder()
                .id(schedule.getId())
                .sourceUser(GroupMemberDto.from(user))
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
