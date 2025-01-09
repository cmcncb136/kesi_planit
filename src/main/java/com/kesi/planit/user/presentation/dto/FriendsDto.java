package com.kesi.planit.user.presentation.dto;

import com.kesi.planit.user.domain.User;
import lombok.Builder;

@Builder
public class FriendsDto {
    public String email;
    public String imgPath;
    public String gender;
    public String nickname;
    public String alias;

    public static FriendsDto from(User user, String alias) {
        return FriendsDto.builder()
                .email(user.getEmail())
                .imgPath(user.getImgPath())
                .gender(user.getGender())
                .nickname(user.getNickname())
                .alias(alias)
                .build();
    }
}
