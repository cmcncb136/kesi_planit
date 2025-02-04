package com.kesi.planit.group.Presentation.dto;

import com.kesi.planit.group.domain.Group;
import lombok.Builder;

@Builder
public class GroupSimpleDto {
    public Long gid;
    public String groupName;
    public int userNumber;

    public static GroupSimpleDto from(Group group) {
        return GroupSimpleDto.builder()
                .gid(group.getGid())
                .groupName(group.getGroupName())
                .userNumber(group.getUsers().size())
                .build();
    }
}
