package com.kesi.planit.group.application.repository;

import com.kesi.planit.group.domain.Group;
import com.kesi.planit.group.infrastructure.GroupJpaEntity;
import com.kesi.planit.group.infrastructure.GroupJpaRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GroupRepo {
    GroupJpaEntity save(GroupJpaEntity groupJpaEntity);
    GroupJpaEntity findById(Long id);
    void deleteById(Long id);
}
