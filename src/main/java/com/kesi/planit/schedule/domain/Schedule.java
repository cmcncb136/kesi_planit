package com.kesi.planit.schedule.domain;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class Schedule {
    private Long id;
    private User maker;
    private Calendar sourceCalendar;
    private Color color;

    private String title;
    private String description;

    private boolean guestEditPermission; //작성자 외 수정권한

    private LocalDate startDate, endDate;  // 종일인 경우
    private LocalDateTime startTime, endTime; //종일이 아닌 경우

    public void createAlarm(){
        
    }
}
