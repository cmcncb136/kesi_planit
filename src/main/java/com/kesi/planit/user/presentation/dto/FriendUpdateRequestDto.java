package com.kesi.planit.user.presentation.dto;

import com.kesi.planit.user.domain.FriendsRelation;
import com.kesi.planit.user.domain.User;

public class FriendUpdateRequestDto {
    public String targetEmail;
    public String alias;

    public FriendsRelation toFriendsRelation(User source, User target) {
        return FriendsRelation.builder()
                .source(source)
                .target(target)
                .alias(alias)
                .build();
    }
}
