package com.kesi.planit.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class User {
    private String uid;
    private String email;
    private String nickname;
    private String imgPath;
    private String gender;
    private String birth;


    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }
}
