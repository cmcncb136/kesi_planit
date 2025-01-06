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

    private boolean isCalendarShared; //그 그룹에 자신의 캘린더를 공유할지
    private boolean isDetailedInfoShared; //그 그룹에 자신의 캘린더 디테일한 정보까지 공유할지

    @Builder
    public GroupAndUserJpaEntity(Long id, String uid, Long gid, boolean isCalendarShared, boolean isDetailedInfoShared) {
        this.id = id;
        this.uid = uid;
        this.gid = gid;
        this.isCalendarShared = isCalendarShared;
        this.isDetailedInfoShared = isDetailedInfoShared;
    }

    public Group.GroupInUser mappingGroupToGroupInUser(User user) {
        return Group.GroupInUser.builder()
                .id(id)
                .user(user)
                .isCalendarShared(isCalendarShared)
                .isDetailedInfoShared(isDetailedInfoShared)
                .build();
    }

    public static GroupAndUserJpaEntity from(Long gid, Group.GroupInUser groupInUser) {
        return GroupAndUserJpaEntity.builder()
                .id(groupInUser.getId())
                .gid(gid)
                .uid(groupInUser.getUser().getUid())
                .isCalendarShared(groupInUser.isCalendarShared())
                .isDetailedInfoShared(groupInUser.isDetailedInfoShared())
                .build();
    }
}
