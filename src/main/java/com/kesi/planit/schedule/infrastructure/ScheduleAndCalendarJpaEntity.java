package com.kesi.planit.schedule.infrastructure;

import com.kesi.planit.schedule.domain.ScheduleAndCalendar;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//DB 상에서 Schedule과 Calendar를 연결해주는 클래스
/*
*
* 현재 한개의 schedule이 여러개의 Calendar를 참조하고
* Calendar에서 여러개의 schedule를 참고 하고 있다.
* 따라서 n : m 관계를 가지고 있는데 중간에 매개할 수 있는 객체를 만들어
* n : 1, 1 :n 으로 만든다.
 */
@Entity
@Getter
@NoArgsConstructor
public class ScheduleAndCalendarJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long calendarId;

    private Long scheduleId;

    @Builder
    public ScheduleAndCalendarJpaEntity(Long id, Long calendarId, Long scheduleId) {
        this.id = id;
        this.calendarId = calendarId;
        this.scheduleId = scheduleId;
    }

    public ScheduleAndCalendar tooModel(){
        return ScheduleAndCalendar.builder()
                .id(id)
                .calendarId(calendarId)
                .scheduleId(scheduleId)
                .build();
    }

    public static ScheduleAndCalendarJpaEntity from(ScheduleAndCalendar scheduleAndCalendar){
        return ScheduleAndCalendarJpaEntity.builder()
                .id(scheduleAndCalendar.getId())
                .calendarId(scheduleAndCalendar.getCalendarId())
                .scheduleId(scheduleAndCalendar.getScheduleId())
                .build();
    }
}
