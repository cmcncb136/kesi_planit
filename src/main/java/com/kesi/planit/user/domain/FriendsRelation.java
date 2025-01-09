package com.kesi.planit.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class  FriendsRelation {
    private User source;
    private User target;
    private String alias;

}
