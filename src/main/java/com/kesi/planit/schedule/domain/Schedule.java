package com.kesi.planit.schedule.domain;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.lang.reflect.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class Schedule {
    private Long id;
    private User maker;
    private List<Calendar> calendars; //참석자 캘린더 및 공유방 캘린더
    private Color color;

    private String title;
    private String description;

    private boolean guestsCanModify; //방장 외 수정 권한

    private LocalDate startDate, endDate;  // 종일인 경우
    private LocalDateTime startTime, endTime; //종일이 아닌 경우

    public void createAlarm(){

    }

    //Todo. CRUD 설계


}
