package com.kesi.planit.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
public class User {
    private String uid;
    private String email;
    private String nickname;
    private String imgPath;
    private String gender;
    private LocalDate birth;
    private LocalDate joinDate;


    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }
}
