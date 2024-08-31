package com.kesi.planit.group.infrastructure;

import com.kesi.planit.user.infrastructure.UserJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupJpaRepo {
    GroupJpaEntity findById(long id);
}
