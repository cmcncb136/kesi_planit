package com.kesi.planit.group.domain;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class Group {
    private final long gid;
    private final String groupName; //추후 그룹이름에 제한이 필요한 경우 VO 객체로 만들어서 처리

    //Group에 User 객체와 유저와 어떻게 연결되어 있는지 가지고 있음 편할듯
    //String은 uid로 식별
    private final GroupUserMap users;
    private final User maker;
    private final Calendar groupCalendar;


    public void addUser(GroupInUser user) {
        users.put(user.getUser().getUid(), user);
    }

    public GroupInUser exitUser(GroupInUser user) {
        return users.remove(user.getUser().getUid());
    }

    public GroupInUser exitUser(String uid) { return users.remove(uid); }


    @Getter
    @Builder
    public static class GroupInUser {
        private Long id;
        private User user;
        private SecurityLevel allowedSecurityLevel;
    }

    public boolean checkMember(User user){
        return users.contains(user.getUid());
    }

    public boolean checkMember(String uid){
        return users.contains(uid);
    }

    public int userNumber() {
        return users.size();
    }

    public List<User> getUserList() {
        return users.getUserList();
    }

    public List<GroupInUser> getGroupInUserList() {
        return users.getGroupInUserList();
    }
}
