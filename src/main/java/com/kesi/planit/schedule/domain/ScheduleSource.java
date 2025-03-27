package com.kesi.planit.schedule.domain;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Builder
@Getter
public class ScheduleSource {
    private Long id;
    private User maker;
    private Calendar sourceCalendar;
    private Color color;

    private String title; //추후 제한을걸 필요가 있으면 VO 객체로 생성
    private String description;

    private boolean guestEditPermission; //작성자 외 수정권한

    private String link;
    private String place;

    private LocalDate startDate, endDate;  // 종일인 경우
    private LocalTime startTime, endTime; //종일이 아닌 경우

    public void createAlarm(){
        
    }

    public void editPossible(Calendar calendar){
        if(guestEditPermission) return;

        if(!Objects.equals(calendar.getId(), sourceCalendar.getId()))
            throw new IllegalArgumentException("calendar does not match source calendar");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleSource that = (ScheduleSource) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
