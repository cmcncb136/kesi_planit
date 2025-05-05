package com.kesi.planit.alarm.application.repository;

import com.kesi.planit.alarm.infrastructure.AlarmJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlarmRepo {
    AlarmJpaEntity findById(long id);
    AlarmJpaEntity save(AlarmJpaEntity alarm);
    List<AlarmJpaEntity> findByUid(String uid);
    Page<AlarmJpaEntity> findAll(Pageable pageable);
    void deleteById(long id);
}
