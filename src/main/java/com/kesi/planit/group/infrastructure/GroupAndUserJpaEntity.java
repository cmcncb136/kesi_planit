package com.kesi.planit.group.infrastructure;

import com.kesi.planit.group.domain.Group;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "group_and_user")
public class GroupAndUserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;
    private Long gid;
    private Integer allowedSecurityLevel; //사용자가 이 그룹에 허용한 보안등급


    @Builder
    public GroupAndUserJpaEntity(Long id, String uid, Long gid, Integer allowedSecurityLevel) {
        this.id = id;
        this.uid = uid;
        this.gid = gid;
        this.allowedSecurityLevel = allowedSecurityLevel;
    }

    public Group.GroupInUser mappingGroupToGroupInUser(User user) {
        return Group.GroupInUser.builder()
                .id(id)
                .allowedSecurityLevel(allowedSecurityLevel)
                .user(user)
                .build();
    }

    public static GroupAndUserJpaEntity from(Long gid, Group.GroupInUser groupInUser) {
        return GroupAndUserJpaEntity.builder()
                .id(groupInUser.getId())
                .gid(gid)
                .uid(groupInUser.getUser().getUid())
                .allowedSecurityLevel(groupInUser.getAllowedSecurityLevel())
                .build();
    }
}
