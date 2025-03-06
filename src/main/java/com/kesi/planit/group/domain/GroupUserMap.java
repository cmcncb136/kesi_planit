package com.kesi.planit.group.domain;

import com.kesi.planit.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupUserMap {
    private final Map<String, Group.GroupInUser> users;

    public GroupUserMap( Map<String, Group.GroupInUser> users) {
        this.users = users;
    }

    public Group.GroupInUser get(String uid) {
        return users.get(uid);
    }

    public User getUser(String uid) {
        return users.get(uid).getUser();
    }

    public void put(String uid, Group.GroupInUser user) {
        users.put(uid, user);
    }

    public List<Group.GroupInUser> getGroupInUserList() {
        return new ArrayList<>(users.values());
    }

    public List<User> getUserList() {
        return new ArrayList<>(users.values().stream().map(Group.GroupInUser::getUser).toList());
    }

    public Group.GroupInUser remove(String uid) {
        return users.remove(uid);
    }

    public boolean contains(String uid) {
        return users.containsKey(uid);
    }

    public boolean contains(User user) {
        return users.containsKey(user.getUid());
    }

    public int size() {
        return users.size();
    }

}
