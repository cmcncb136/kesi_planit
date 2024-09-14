package com.kesi.planit.group.infrastructure;

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
    public GroupAndUserJpaEntity(String uid, Long gid, boolean access, boolean open) {
        this.uid = uid;
        this.gid = gid;
        this.access = access;
        this.open = open;
    }
}
