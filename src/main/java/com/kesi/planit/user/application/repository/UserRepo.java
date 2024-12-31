package com.kesi.planit.user.application.repository;

import com.kesi.planit.user.infrastructure.UserJpaEntity;
import org.springframework.stereotype.Component;

public interface UserRepo {
    UserJpaEntity findById(String uid);
    UserJpaEntity findByEmail(String email);
    UserJpaEntity save(UserJpaEntity user);
}
