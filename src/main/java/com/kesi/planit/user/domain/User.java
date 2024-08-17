package com.kesi.planit.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class User {

    //todo 프론트에서 값 보내면 type 지정이 어떻게 되더라?
    private Long id;
    private String email;
    private String birthdate;
    private boolean gender;
    private String nickname;

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }
}
