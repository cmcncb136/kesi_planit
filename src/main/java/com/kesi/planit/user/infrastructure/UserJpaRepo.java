package com.kesi.planit.user.infrastructure;

import com.kesi.planit.user.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepo extends JpaRepository<UserJpaEntity, String> {
    UserJpaEntity findByEmail(String email);
    @NotNull Page<UserJpaEntity> findAll(@NotNull Pageable pageable);
}


