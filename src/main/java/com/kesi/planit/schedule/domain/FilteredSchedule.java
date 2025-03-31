package com.kesi.planit.schedule.domain;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class FilteredSchedule {
    private final Long id;
    private final User sourceUser;
    private final Calendar sourceCalendar;
    private final Color color;

    private final String title; //추후 제한을걸 필요가 있으면 VO 객체로 생성
    private final String description;

    private final boolean guestEditPermission; //작성자 외 수정권한

    private final String link;
    private final String place;

    private final LocalDate startDate, endDate;  // 종일인 경우
    private final LocalTime startTime, endTime; //종일이 아닌 경우

    public static FilteredSchedule toFilteredSchedule(ScheduleSecurity security, Group group, User user) {
        ScheduleSource schedule = security.getSchedule();
        boolean lock =
                security.getSecurityLevel().ordinal() < group.getGroupInUser(user.getUid()).getAllowedSecurityLevel().ordinal();

        return FilteredSchedule.builder()
                .id(schedule.getId())
                .sourceUser(user)
                .sourceCalendar(schedule.getSourceCalendar())
                .color(lock ? Color.GRAY : schedule.getColor())
                .title(lock ? null : schedule.getTitle())
                .description(lock ? null : schedule.getDescription())
                .guestEditPermission(schedule.isGuestEditPermission())
                .link(lock ? null : schedule.getLink())
                .place(lock ? null : schedule.getPlace())
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .build();
    }
}
