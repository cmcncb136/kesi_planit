package com.kesi.planit.user.presentation.dto;

import com.kesi.planit.core.role.Role;
import com.kesi.planit.user.domain.User;
import lombok.Builder;

@Builder
public class UserWithUidDto {
    public String uid;
    public String email;
    public String nickName;
    public String imgPath;
    public String gender;
    public String birth;
    public String joinDate;
    public Role role;

    public static UserWithUidDto from(User user) {
        return UserWithUidDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickname())
                .imgPath(user.getImgPath())
                .gender(user.getGender())
                .birth(user.getBirth().toString())
                .joinDate(user.getJoinDate().toString())
                .role(user.getRole())
                .uid(user.getUid())
                .build();
    }

}
