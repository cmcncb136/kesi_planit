package com.kesi.planit.group.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupAndUserJpaRepo extends JpaRepository<GroupAndUserJpaEntity, Long> {
    List<GroupAndUserJpaEntity> findByGid(Long gid);
    List<GroupAndUserJpaEntity> findByUid(String uid);
}
