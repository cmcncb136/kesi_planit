package com.kesi.planit.group.application.repository;

import com.kesi.planit.group.infrastructure.GroupAndUserJpaEntity;

import java.util.List;


public interface GroupAndUserRepo {
    GroupAndUserJpaEntity findById(Long id);
    GroupAndUserJpaEntity findByUidAndGid(String uid, Long gid);
    GroupAndUserJpaEntity save(GroupAndUserJpaEntity groupAndUser);
    List<GroupAndUserJpaEntity> saveAll(List<GroupAndUserJpaEntity> groupAndUsers);
    List<GroupAndUserJpaEntity> findByGid(Long gid);
    List<GroupAndUserJpaEntity> findByUid(String uid);
    void deleteById(Long id);
    void deleteByUidAndGid(String uid, Long gid);
}
