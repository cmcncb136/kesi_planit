package com.kesi.planit.user.infrastructure;

import com.kesi.planit.user.application.repository.FriendsRelationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FriendsRelationRepoImpl implements FriendsRelationRepo {
    private final FriendsRelationJpaRepo friendsRelationJpaRepo;

    @Override
    public List<FriendsRelationJpaEntity> findBySourceEmail(String sourceEmail) {
        return friendsRelationJpaRepo.findBySourceEmail(sourceEmail);
    }

    @Override
    public List<FriendsRelationJpaEntity> findByTargetEmail(String targetEmail) {
        return friendsRelationJpaRepo.findByTargetEmail(targetEmail);
    }

    @Override
    public FriendsRelationJpaEntity findBySourceEmailAndTargetEmail(String sourceEmail, String targetEmail) {
        return friendsRelationJpaRepo.findBySourceEmailAndTargetEmail(sourceEmail, targetEmail);
    }

    @Override
    public FriendsRelationJpaEntity save(FriendsRelationJpaEntity friendsRelation) {
        return friendsRelationJpaRepo.save(friendsRelation);
    }

    @Override
    public FriendsRelationJpaEntity findById(Long id) {
        return friendsRelationJpaRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        friendsRelationJpaRepo.deleteById(id);
    }
}
