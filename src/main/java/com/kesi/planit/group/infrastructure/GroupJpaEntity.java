package com.kesi.planit.group.infrastructure;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.group.domain.Group;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Table(name = "group_table")
@NoArgsConstructor
@Entity
public class GroupJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    private String groupName;

    private String makerUid;

    private Long calendarId;

    @Builder
    public GroupJpaEntity(Long gid, String groupName, String makerUid, Long calendarId) {
        this.gid = gid;
        this.groupName = groupName;
        this.makerUid = makerUid;
        this.calendarId = calendarId;
    }

    public static GroupJpaEntity from(Group group) {
       return GroupJpaEntity.builder()
               .gid(group.getGid())
               .groupName(group.getGroupName())
               .makerUid(group.getMaker().getUid())
               .calendarId(group.getGroupCalendar().getId())
               .build();
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
