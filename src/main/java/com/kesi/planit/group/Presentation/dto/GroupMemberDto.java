package com.kesi.planit.group.Presentation.dto;

import com.kesi.planit.user.domain.User;
import lombok.Builder;

@Builder
public class GroupMemberDto {
    public String email;
    public String nickName;
    public String imgPath;
    public String gender;

    public static GroupMemberDto from(User user) {
        return GroupMemberDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickname())
                .imgPath(user.getImgPath())
                .gender(user.getGender())
                .build();
    }
}
