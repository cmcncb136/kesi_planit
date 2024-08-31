package com.kesi.planit.user.application.repository;

import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.infrastructure.UserJpaEntity;

public interface UserRepo {
    UserJpaEntity findById(String uid);

    void save(UserJpaEntity user);
}
