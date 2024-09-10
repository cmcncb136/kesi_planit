package com.kesi.planit.user.infrastructure;

import com.kesi.planit.user.application.repository.UserRepo;
import com.kesi.planit.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
public class UserRepoImpl implements UserRepo {

    private final UserJpaRepo userJpaRepo;

    @Override
    public UserJpaEntity findById(String uid) {
        return userJpaRepo.findById(uid).orElse(null);
    }

    @Override
    public UserJpaEntity findByEmail(String email) {
        return userJpaRepo.findByEmail(email);
    }

    @Override
    public UserJpaEntity save(UserJpaEntity user) {
        return userJpaRepo.save(user);
    }
}
