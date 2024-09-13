package com.kesi.planit.group.domain;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class Group {
    private long gid;

    private String groupName;
    private List<User> users;
    private User maker;
    private Calendar groupCalendar;


    public void addUser(User user) {
        users.add(user);
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }

    public List<User> getUsers() {
        return users;
    }

}
