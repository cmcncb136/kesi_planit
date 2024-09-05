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
import org.springframework.cglib.core.Local;

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

    private String colorId; //#XXXXXXXX로 관리

    private String description;
    private String title;

    private Boolean guestsCanModify;

    private LocalDate startDate, endDate;
    private LocalDateTime startTime, endTime;

    @Builder
    public ScheduleJpaEntity(String makerUid, String colorId, String description, String title, Boolean guestsCanModify, LocalDate startDate, LocalDate endDate, LocalDateTime startTime, LocalDateTime endTime) {
        this.makerUid = makerUid;
        this.colorId = colorId;
        this.description = description;
        this.title = title;
        this.guestsCanModify = guestsCanModify;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public Schedule toModel(User user, List<Calendar> calendars){
        return Schedule.builder()
                .color(Color.decode(colorId))
                .maker(user)
                .title(title)
                .description(description)
                .guestsCanModify(guestsCanModify)
                .startDate(startDate)
                .endDate(endDate)
                .startTime(startTime)
                .endTime(endTime)
                .calendars(calendars)
                .build();
    }

    public static ScheduleJpaEntity from(Schedule schedule){
        return ScheduleJpaEntity.builder()
                .colorId(schedule.getColor().toString())
                .makerUid(schedule.getMaker().getUid())
                .description(schedule.getDescription())
                .title(schedule.getTitle())
                .endDate(schedule.getEndDate())
                .startDate(schedule.getStartDate())
                .endTime(schedule.getEndTime())
                .startTime(schedule.getStartTime())
                .build();
    }

}
