package com.kesi.planit.group.Presentation.dto;

import com.kesi.planit.group.domain.Group;
import lombok.Builder;

import java.util.List;

@Builder
public class GroupDto {
    public Long gid;
    public String groupName;
    public GroupMemberDto maker;
    public Long calendarId;
    public List<GroupMemberDto> members;


    public static GroupDto from(Group group){
        return GroupDto.builder()
                .gid(group.getGid())
                .groupName(group.getGroupName())
                .maker(GroupMemberDto.from(group.getMaker()))
                .members(group.getUserList().stream().map(GroupMemberDto::from).toList())
                .calendarId(group.getGroupCalendar().getId())
                .build();
    }
}
