package com.kesi.planit.user.application.repository;

import com.kesi.planit.user.domain.User;

public interface UserRepo {
    public User findById(long id);

    public void save(User user);
}
