package com.kesi.planit.user.application.repository;

import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.infrastructure.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserRepo {
    UserJpaEntity findById(String uid);

    UserJpaEntity save(UserJpaEntity user);
}
