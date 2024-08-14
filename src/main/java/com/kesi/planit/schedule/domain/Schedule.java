package com.kesi.planit.schedule.domain;

import com.kesi.planit.calendar.domain.Calendar;

import java.awt.*;
import java.lang.reflect.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Schedule {
    private Member maker;
    private List<Calendar> calendars; //참석자
    private Color color;

    private String title;
    private String description;

    private boolean guestsCanModify;

    private LocalDate startDate, endDate;  // 종일인 경우
    private LocalDateTime startTime, endTime; //종일이 아닌 경우

    public void createAlarm(){

    }

    //Todo. CRUD 설계


}
