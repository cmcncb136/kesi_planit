package com.kesi.planit.schedule.infrastructure;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
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
@Table(name = "schedule_and_calendar")
public class ScheduleAndCalendarJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long calendarId;
    private Long scheduleId;
    private String connectUserUid;

    //일정을 조회할 때 Group에 Calendar와 연결된 Schedule를 조회하게 된다.
    //근데 connectUserUid 정보가 없으면 누구에 스케줄인줄 알 수 없다.
    //개인 일정이라면 문제가 없는데 Group에 일정인 경우 문제가 발생할 수 있다.
    //User u1, u2, u3에, Group은 g1, g2가 있다고 하자
    //g1 그룹에 맴버가 u1, u2라고 하고 g2가 u1, u2, u3라고 하면
    //g1에 일정이 추가된 경우 g1 그룹 일정과 u1, u2에 일정에 추가되는데
    //u1와 u2에 일정이 g2에 추가되는 경우 누구에 일정인지 알기 힘들다.


    @Builder
    public ScheduleAndCalendarJpaEntity(Long id, Long calendarId, Long scheduleId, String connectUserUid) {
        this.id = id;
        this.calendarId = calendarId;
        this.scheduleId = scheduleId;
        this.connectUserUid = connectUserUid;
    }

    public Schedule.ScheduleReferCalendar toScheduleReferCalendar(Calendar calendar, User user) {
        return Schedule.ScheduleReferCalendar.builder()
                .id(id)
                .calendar(calendar)
                .connectUser(user)
                .build();
    }

    public static ScheduleAndCalendarJpaEntity from(Schedule schedule, Schedule.ScheduleReferCalendar scheduleReferCalendar) {
        return ScheduleAndCalendarJpaEntity.builder()
                .id(scheduleReferCalendar.getId())
                .calendarId(scheduleReferCalendar.getCalendar().getId())
                .scheduleId(schedule.getId())
                .build();
    }

}
