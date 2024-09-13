package com.kesi.planit.user.presentation.dto;

import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
public class UserDto {
    public String email;
    public String nickName;
    public String imgPath;
    public String gender;
    public String birth;
    public String joinDate;

    public static UserDto from(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickname())
                .imgPath(user.getImgPath())
                .gender(user.getGender())
                .birth(user.getBirth().toString())
                .joinDate(user.getJoinDate().toString())
                .build();
    }
}
