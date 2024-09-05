package com.kesi.planit.calendar.domain;

import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

//Maker 정보

@Builder
@Getter
public class UserAndCalendar {
    private Long id;
    private Calendar calendar;
    private User user;

}
