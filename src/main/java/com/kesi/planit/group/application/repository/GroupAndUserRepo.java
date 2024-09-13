package com.kesi.planit.group.application.repository;

import com.kesi.planit.group.infrastructure.GroupAndUserJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GroupAndUserRepo {
    GroupAndUserJpaEntity findById(Long id);
    GroupAndUserJpaEntity save(GroupAndUserJpaEntity groupAndUser);
    List<GroupAndUserJpaEntity> findByGid(Long gid);
    List<GroupAndUserJpaEntity> findByUid(String uid);
    void deleteById(Long id);
}
