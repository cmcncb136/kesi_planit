package com.kesi.planit.user.application.repository;

import com.kesi.planit.user.infrastructure.UserJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepo {
    UserJpaEntity findById(String uid);
    UserJpaEntity findByEmail(String email);
    Page<UserJpaEntity> findAll(Pageable pageable);
    UserJpaEntity save(UserJpaEntity user);
}
