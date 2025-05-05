package com.kesi.planit.schedule.presentation.dto;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.user.domain.User;
import lombok.Builder;

import java.awt.*;

@Builder
public class ScheduleSourceDto {
    public Long id;
    public String makerUid;
    public Long sourceCalendarId;
    public int color;

    public String title;
    public String description;

    public boolean guestEditPermission;

    public String link;
    public String place;

    public String startDate, endDate;
    public String startTime, endTime;

    public static ScheduleSourceDto from(ScheduleSource scheduleSource){
        return ScheduleSourceDto.builder()
                .id(scheduleSource.getId())
                .makerUid(scheduleSource.getMaker().getUid())
                .sourceCalendarId(scheduleSource.getSourceCalendar().getId())
                .color(scheduleSource.getColor().getRGB())
                .title(scheduleSource.getTitle())
                .description(scheduleSource.getDescription())
                .guestEditPermission(scheduleSource.isGuestEditPermission())
                .link(scheduleSource.getLink())
                .place(scheduleSource.getPlace())
                .startDate(scheduleSource.getStartDate().toString())
                .endDate(scheduleSource.getEndDate().toString())
                .startTime(scheduleSource.getStartTime().toString())
                .endTime(scheduleSource.getEndTime().toString())
                .build();
    }
}















