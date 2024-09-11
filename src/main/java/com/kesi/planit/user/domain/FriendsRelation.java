package com.kesi.planit.user.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FriendsRelation {
    private User source;
    private User target;

}
