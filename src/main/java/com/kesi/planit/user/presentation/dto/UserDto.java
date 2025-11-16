package com.kesi.planit.user.presentation.dto;

import com.kesi.planit.core.role.Role;
import com.kesi.planit.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class UserDto {
    @Schema(description = "사용자 이메일", example = "test@example.com")
    public String email;

    @Schema(description = "사용자 닉네임", example = "홍길동")
    public String nickName;

    @Schema(description = "프로필 이미지 경로(URL)", example = "https://img.example.com/profile/user123.jpg")
    public String imgPath;

    @Schema(description = "성별", example = "male")
    public String gender;

    @Schema(description = "생년월일 (YYYY-MM-DD)", example = "2000-01-15")
    public String birth;

    @Schema(description = "가입일 (YYYY-MM-DD)", example = "2024-08-12")
    public String joinDate;

    @Schema(description = "권한", example = "USER")
    public Role role;

    public static UserDto from(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickname())
                .imgPath(user.getImgPath())
                .gender(user.getGender())
                .birth(user.getBirth().toString())
                .joinDate(user.getJoinDate().toString())
                .role(user.getRole())
                .build();
    }
}
