package com.kesi.planit.user.presentation.dto;

import com.kesi.planit.user.domain.User;
import lombok.Builder;

@Builder
public class FriendsDto {
    public String email;
    public String imgPath;
    public String gender;
    public String nickname;

    public static FriendsDto from(User user){
        return FriendsDto.builder()
                .email(user.getEmail())
                .imgPath(user.getImgPath())
                .gender(user.getGender())
                .nickname(user.getNickname())
                .build();
    }
}
