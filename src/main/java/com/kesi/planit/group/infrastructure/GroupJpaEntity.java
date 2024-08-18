package com.kesi.planit.group.infrastructure;

import com.kesi.planit.group.domain.Group;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class GroupJpaEntity {

    @Id
    private Long groupId;

    @Column(nullable = true)
    private String groupName;

    @Column(nullable = true)
    //todo: OneToMany를 안쓰고 하는 방법을 잘 모르겠다
    @ElementCollection
    private List<String> uids;

    public static GroupJpaEntity from(Group group) {
        GroupJpaEntity result = new GroupJpaEntity();
        result.groupId = group.getGroupId();
        result.groupName = group.getGroupName();
        result.uids = group.getUsers();
        return result;
    }

    public Group toModel(){
    return Group.builder()
            .groupId(groupId)
            .groupName(groupName)
            .build();
    }
}
