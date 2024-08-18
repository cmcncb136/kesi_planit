package com.kesi.planit.group.domain;

import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class Group {
    //todo 채팅방 관련
    private long groupId;
    private String groupName;
    private List<String> uids;

    //todo a. UID로 그룹을 형성, b. user 객체로 그룹형성

    public void addUser(String uid){
       uids.add(uid);
    }

    public boolean removeUser(String uid) {
        return uids.remove(uid);
    }

    public List<String> getUsers() {return new ArrayList<>(uids);}

    public int size() {return uids.size();}
}
