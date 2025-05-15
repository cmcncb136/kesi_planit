package com.kesi.planit.schedule.infrastructure;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "schedule_source", indexes = {
        @Index(name = "idx_schedule_start_date", columnList = "start_date"),
        @Index(name = "idx_schedule_end_date", columnList = "end_date")
})
public class ScheduleJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String makerUid;
    private Long sourceCalendarId; //만들어진 캘린더 정보
    private String colorValue;

    private String description;
    private String title;

    private String link;
    private String place;

    private Boolean guestEditPermission;

    private LocalDate startDate, endDate;
    private LocalTime startTime, endTime;

    @Builder
    public ScheduleJpaEntity(Long id, String makerUid, int colorValue,
                             String description, String title, String link, String place,
                             Boolean guestEditPermission, LocalDate startDate,
                             LocalDate endDate, LocalTime startTime,
                             LocalTime endTime, Long sourceCalendarId) {
        this.id = id;
        this.makerUid = makerUid;
        this.colorValue = Integer.toHexString(colorValue);
        this.description = description;
        this.title = title;
        this.link = link;
        this.place = place;
        this.guestEditPermission = guestEditPermission;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sourceCalendarId = sourceCalendarId;
    }


    public ScheduleSource toModel(User user, Calendar sourceCalendar){
        return ScheduleSource.builder()
                .id(id)
                .color(new Color((int)(Long.parseLong(colorValue, 16)), true))
                .maker(user)
                .title(title)
                .description(description)
                .link(link)
                .place(place)
                .guestEditPermission(guestEditPermission)
                .startDate(startDate)
                .endDate(endDate)
                .startTime(startTime)
                .endTime(endTime)
                .sourceCalendar(sourceCalendar)
                .build();
    }

    public static ScheduleJpaEntity from(ScheduleSource schedule){
        return ScheduleJpaEntity.builder()
                .id(schedule.getId())
                .colorValue(schedule.getColor().getRGB())
                .makerUid(schedule.getMaker().getUid())
                .description(schedule.getDescription())
                .title(schedule.getTitle())
                .link(schedule.getLink())
                .place(schedule.getPlace())
                .endDate(schedule.getEndDate())
                .startDate(schedule.getStartDate())
                .endTime(schedule.getEndTime())
                .startTime(schedule.getStartTime())
                .sourceCalendarId(schedule.getSourceCalendar().getId())
                .guestEditPermission(schedule.isGuestEditPermission())
                .build();
    }

}
