package com.kesi.planit.group.infrastructure;

import com.kesi.planit.group.application.repository.GroupRepo;
import com.kesi.planit.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public class GroupJpaRepoImpl implements GroupRepo {

    GroupJpaEntity groupJpaEntity;

    @Override
    public void save(Group group) {
    }
}
