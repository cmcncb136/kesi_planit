package com.kesi.planit.schedule.infrastructure;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.domain.Schedule;
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
@Table(name = "schedule")
public class ScheduleJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String makerUid;
    private Long sourceCalendarId; //만들어진 캘린더 정보
    private String colorValue; //#XXXXXXXX로 관리

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
        this.colorValue = String.valueOf(colorValue);
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


    public Schedule toModel(User user, Calendar sourceCalendar){
        return Schedule.builder()
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

    public static ScheduleJpaEntity from(Schedule schedule){
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
                .build();
    }

}
