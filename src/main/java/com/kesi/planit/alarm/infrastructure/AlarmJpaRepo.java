package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.domain.Alarm;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmJpaRepo extends JpaRepository<AlarmJpaEntity, Long > {
    List<AlarmJpaEntity> findByUid(String uid);
    @NotNull Page<AlarmJpaEntity> findAll(@NotNull Pageable pageable);
}
