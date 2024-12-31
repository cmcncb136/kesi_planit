package com.kesi.planit.user.presentation.dto;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequestDto {
    private String nickname;
    private String gender;
    private String birth;

    public User toModel(String uid, String email, String imgPath, LocalDate joinDate, Calendar calendar){
        return User.builder()
                .nickname(nickname)
                .gender(gender)
                .uid(uid)
                .email(email)
                .imgPath(imgPath)
                .joinDate(joinDate)
                .birth(LocalDate.parse(birth))
                .myCalendar(calendar)
                .build();
    }
}
