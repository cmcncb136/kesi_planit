package com.kesi.planit.user.application.repository;

import com.kesi.planit.user.infrastructure.FriendsRelationJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FriendsRelationRepo {
    List<FriendsRelationJpaEntity> findBySourceEmail(String sourceEmail);
    List<FriendsRelationJpaEntity> findByTargetEmail(String targetEmail);
    FriendsRelationJpaEntity findBySourceEmailAndTargetEmail(String sourceEmail, String targetEmail);
    FriendsRelationJpaEntity save(FriendsRelationJpaEntity friendsRelation);
    FriendsRelationJpaEntity findById(Long id);
    void deleteById(Long id);
}
