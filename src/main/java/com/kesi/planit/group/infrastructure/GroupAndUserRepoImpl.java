package com.kesi.planit.group.infrastructure;

import com.kesi.planit.group.application.repository.GroupAndUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GroupAndUserRepoImpl implements GroupAndUserRepo {
    private final GroupAndUserJpaRepo groupAndUserJpaRepo;
    @Override
    public GroupAndUserJpaEntity findById(Long id) {
        return groupAndUserJpaRepo.findById(id).orElse(null);
    }

    @Override
    public GroupAndUserJpaEntity save(GroupAndUserJpaEntity groupAndUser) {
        return groupAndUserJpaRepo.save(groupAndUser);
    }

    @Override
    public List<GroupAndUserJpaEntity> findByGid(Long gid) {
        return groupAndUserJpaRepo.findByGid(gid);
    }

    @Override
    public List<GroupAndUserJpaEntity> findByUid(String uid) {
        return groupAndUserJpaRepo.findByUid(uid);
    }

    @Override
    public void deleteById(Long id) {

    }
}
