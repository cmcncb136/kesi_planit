package com.kesi.planit.group.infrastructure;

import com.kesi.planit.group.domain.Group;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class GroupAndUserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;
    private Long gid;

    private boolean access; //그 그룹에 자신의 캘린더를 공유할지
    private boolean open; //그 그룹에 자신의 캘린더 디테일한 정보까지 공유할지

    @Builder
    public GroupAndUserJpaEntity(Long id, String uid, Long gid, boolean access, boolean open) {
        this.id = id;
        this.uid = uid;
        this.gid = gid;
        this.access = access;
        this.open = open;
    }

    public Group.GroupInUser mappingGroupToGroupInUser(User user) {
        return Group.GroupInUser.builder()
                .id(id)
                .user(user)
                .access(access)
                .open(open)
                .build();
    }

    public static GroupAndUserJpaEntity from(Long gid, Group.GroupInUser groupInUser) {
        return GroupAndUserJpaEntity.builder()
                .id(groupInUser.getId())
                .gid(gid)
                .uid(groupInUser.getUser().getUid())
                .open(groupInUser.isOpen())
                .access(groupInUser.isAccess())
                .build();
    }
}
