package com.kesi.planit.group.application.repository;

import com.kesi.planit.group.domain.Group;
import com.kesi.planit.group.infrastructure.GroupJpaRepo;

public interface GroupRepo {
    public GroupJpaRepo groupJpaRepo;
    public void save(Group group);
}
