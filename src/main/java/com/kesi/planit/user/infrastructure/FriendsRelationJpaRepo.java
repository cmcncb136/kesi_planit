package com.kesi.planit.user.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRelationJpaRepo extends JpaRepository<FriendsRelationJpaEntity, Long> {
    List<FriendsRelationJpaEntity> findBySourceEmail(String sourceEmail);
    List<FriendsRelationJpaEntity> findByTargetEmail(String targetEmail);
    FriendsRelationJpaEntity findBySourceEmailAndTargetEmail(String sourceEmail, String targetEmail);
}
