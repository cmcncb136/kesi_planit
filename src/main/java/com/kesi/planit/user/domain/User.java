package com.kesi.planit.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class User {
    private Long id;
    private String email;
    private String nickname;

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }
}
