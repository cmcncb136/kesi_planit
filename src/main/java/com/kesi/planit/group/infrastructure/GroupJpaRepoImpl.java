package com.kesi.planit.group.infrastructure;

import com.kesi.planit.group.application.repository.GroupRepo;
import com.kesi.planit.group.domain.Group;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GroupJpaRepoImpl implements GroupRepo {
    private final GroupJpaRepo groupJpaRepo;


    @Override
    public GroupJpaEntity save(GroupJpaEntity groupJpaEntity) {
        return groupJpaRepo.save(groupJpaEntity);
    }

    @Override
    public GroupJpaEntity findById(Long id) {
        return groupJpaRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        groupJpaRepo.deleteById(id);
    }
}
