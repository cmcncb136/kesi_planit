package com.kesi.planit.user.presentation.dto;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.core.role.Role;
import com.kesi.planit.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "사용자 닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "성별", example = "male")
    private String gender;

    @Schema(description = "생년월일 (YYYY-MM-DD)", example = "2000-01-15")
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
                .role(Role.USER)
                .build();
    }
}
