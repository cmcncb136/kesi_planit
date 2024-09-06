package com.kesi.planit.group.application.repository;

import com.kesi.planit.group.domain.Group;
import com.kesi.planit.group.infrastructure.GroupJpaRepo;
import org.springframework.stereotype.Component;

@Component
public interface GroupRepo {
    //public GroupJpaRepo groupJpaRepo;
    public void save(Group group);
}
