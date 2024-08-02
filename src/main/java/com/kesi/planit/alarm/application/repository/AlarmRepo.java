package com.kesi.planit.alarm.application.repository;

import com.kesi.planit.alarm.infrastructure.AlarmJpaEntity;

import java.util.List;

public interface AlarmRepo {
    AlarmJpaEntity findById(long id);
    AlarmJpaEntity save(AlarmJpaEntity alarm);
    List<AlarmJpaEntity> findAll();
    void deleteById(long id);
}
