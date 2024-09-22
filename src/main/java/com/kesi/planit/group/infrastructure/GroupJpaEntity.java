package com.kesi.planit.group.infrastructure;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Entity
@Table(name = "group")
public class GroupJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    @Column(nullable = true)
    private String groupName;

    private String makerUid;

    private Long calendarId;

    public static GroupJpaEntity from(Group group) {
        GroupJpaEntity result = new GroupJpaEntity();
        result.gid = group.getGid();
        result.groupName = group.getGroupName();
        result.makerUid = group.getMaker().getUid();
        result.calendarId = group.getGroupCalendar().getId();
        return result;
    }

    public Group toModel(Map<String, Group.GroupInUser> users, Calendar calendar) {
        return Group.builder()
                .gid(gid)
                .groupName(groupName)
                .users(users)
                .groupCalendar(calendar)
                .build();
    }
}
