package com.kesi.planit.user.infrastructure;

import com.kesi.planit.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepo extends JpaRepository<UserJpaEntity, String> {
}
