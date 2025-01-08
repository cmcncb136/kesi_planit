package com.kesi.planit.schedule.infrastructure;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ScheduleJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String makerUid;
    private Long sourceCalendarId; //만들어진 캘린더 정보
    private String colorId; //#XXXXXXXX로 관리

    private String description;
    private String title;

    private Boolean guestEditPermission;

    private LocalDate startDate, endDate;
    private LocalDateTime startTime, endTime;

    @Builder
    public ScheduleJpaEntity(Long id, String makerUid, String colorId,
                             String description, String title,
                             Boolean guestEditPermission, LocalDate startDate,
                             LocalDate endDate, LocalDateTime startTime,
                             LocalDateTime endTime, Long sourceCalendarId) {
        this.id = id;
        this.makerUid = makerUid;
        this.colorId = colorId;
        this.description = description;
        this.title = title;
        this.guestEditPermission = guestEditPermission;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sourceCalendarId = sourceCalendarId;
    }


    public Schedule toModel(User user, Calendar sourceCalendar, List<Schedule.ScheduleReferCalendar> scheduleReferCalendars){
        return Schedule.builder()
                .id(id)
                .color(Color.decode(colorId))
                .maker(user)
                .title(title)
                .description(description)
                .guestEditPermission(guestEditPermission)
                .startDate(startDate)
                .endDate(endDate)
                .startTime(startTime)
                .endTime(endTime)
                .calendars(scheduleReferCalendars)
                .sourceCalendar(sourceCalendar)
                .build();
    }

    public static ScheduleJpaEntity from(Schedule schedule){
        return ScheduleJpaEntity.builder()
                .id(schedule.getId())
                .colorId(schedule.getColor().toString())
                .makerUid(schedule.getMaker().getUid())
                .description(schedule.getDescription())
                .title(schedule.getTitle())
                .endDate(schedule.getEndDate())
                .startDate(schedule.getStartDate())
                .endTime(schedule.getEndTime())
                .startTime(schedule.getStartTime())
                .sourceCalendarId(schedule.getSourceCalendar().getId())
                .build();
    }

}
