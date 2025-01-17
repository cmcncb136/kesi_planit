package com.kesi.planit.group.domain;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Builder
@Getter
public class Group {
    private long gid;
    private String groupName;

    //Group에 User 객체와 유저와 어떻게 연결되어 있는지 가지고 있음 편할듯
    //String은 uid로 식별
    private Map<String, GroupInUser> users;
    private User maker;
    private Calendar groupCalendar;


    public void addUser(GroupInUser user) {
        users.put(user.getUser().getUid(), user);
    }

    public GroupInUser removeUser(GroupInUser user) {
        return users.remove(user.getUser().getUid());
    }


    @Getter
    @Builder
    public static class GroupInUser {
        private Long id;
        private User user;
        private SecurityLevel allowedSecurityLevel;
    }
}
