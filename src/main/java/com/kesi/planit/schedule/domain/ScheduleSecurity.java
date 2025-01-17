package com.kesi.planit.schedule.domain;

import com.kesi.planit.group.domain.Group;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ScheduleSecurity {
    private long id;
    private Schedule schedule;
    private User user;
    private SecurityLevel securityLevel;

    public Schedule getSchedule(Group group) {
        //Todo. security 보다 낮은 경우 날짜 정보만 보내줌.
        if(group.getUsers().get(user.getUid()).getAllowedSecurityLevel().ordinal()
                > securityLevel.ordinal()){
            return schedule;
        }

        return schedule;
    }
}
