package com.kesi.planit.group.domain;

import com.kesi.planit.user.domain.User;

import java.util.ArrayList;
import java.util.List;

public class Group<User> {
    //todo 채팅방 관련
    private long groupId;
    private List<User> users;

    //todo 그룹 식별자 및 생성자에 대한 로직

    public void addUser(User user){
        users.add(user);
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public int size() {
        return users.size();
    }
}
