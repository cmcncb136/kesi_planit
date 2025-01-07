package com.kesi.planit.user.presentation.dto;

import com.kesi.planit.user.domain.User;

import java.time.LocalDate;

public class UserUpdateRequestDto {
    public String nickName;
    public String imgPath;
    public String gender;
    public String birth;

    public User toDomain(User user){
        return User.builder()
                .myCalendar(user.getMyCalendar())
                .uid(user.getUid())
                .birth(LocalDate.parse(birth))
                .gender(gender)
                .imgPath(imgPath)
                .nickname(nickName)
                .email(user.getEmail())
                .joinDate(user.getJoinDate())
                .build();
    }
}
