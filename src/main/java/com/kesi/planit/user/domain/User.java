package com.kesi.planit.user.domain;

import com.kesi.planit.calendar.domain.Calendar;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Builder
@Getter
public class User {
    private final String uid;
    private final String email;
    private String nickname;
    private final String imgPath;
    private String gender;
    private LocalDate birth;
    private final LocalDate joinDate;
    private final Calendar myCalendar;
    private final Role role;

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uid, user.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uid);
    }
}
