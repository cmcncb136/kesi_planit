package com.kesi.planit.user.infrastructure;

import com.kesi.planit.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, Integer> {
    User findById(long id);
}
