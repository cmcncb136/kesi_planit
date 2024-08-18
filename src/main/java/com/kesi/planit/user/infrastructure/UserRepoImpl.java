package com.kesi.planit.user.infrastructure;

import com.kesi.planit.user.application.repository.UserRepo;
import com.kesi.planit.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepoImpl implements UserRepo {

    private final UserJpaRepo userJpaRepo;

    @Override
    public UserJpaEntity findById(long id) {
        return userJpaRepo.findById(id);
    }

    @Override
    public void save(User user) {
    }
}
