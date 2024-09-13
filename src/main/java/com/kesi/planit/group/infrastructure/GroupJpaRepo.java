package com.kesi.planit.group.infrastructure;

import com.kesi.planit.user.infrastructure.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupJpaRepo extends JpaRepository<GroupJpaEntity, Long> {
}
