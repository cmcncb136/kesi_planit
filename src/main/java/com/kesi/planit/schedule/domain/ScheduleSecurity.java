package com.kesi.planit.schedule.domain;

import com.kesi.planit.group.domain.Group;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.awt.*;


@Getter
@Builder
public class ScheduleSecurity {
    private final long id;
    private final Schedule schedule;
    private final User user;
    private SecurityLevel securityLevel;

    public Schedule getSchedule(Group group) {
        //Todo. security 보다 낮은 경우 날짜 정보만 보내줌.
        if(group.getUsers().get(user.getUid()).getAllowedSecurityLevel().ordinal()
                > securityLevel.ordinal()){
            return Schedule.builder()
                    .id(schedule.getId())
                    .maker(user)
                    .color(Color.gray)
                    .sourceCalendar(schedule.getSourceCalendar())
                    .startDate(schedule.getStartDate())
                    .endDate(schedule.getEndDate())
                    .startTime(schedule.getStartTime())
                    .endTime(schedule.getEndTime())
                    .title(null)
                    .guestEditPermission(false)
                    .description(null)
                    .build();
        }

        return schedule;
    }
}
