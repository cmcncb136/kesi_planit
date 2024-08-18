package com.kesi.planit.user.application.repository;

import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.infrastructure.UserJpaEntity;

public interface UserRepo {
    public UserJpaEntity findById(long id);

    public void save(User user);
}
