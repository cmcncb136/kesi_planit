package com.kesi.planit.user.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRelationJpaRepo extends JpaRepository<FriendsRelationJpaEntity, Long> {
    List<FriendsRelationJpaEntity> findSourceEmail(String sourceEmail);
    List<FriendsRelationJpaEntity> findTargetEmail(String targetEmail);
}
