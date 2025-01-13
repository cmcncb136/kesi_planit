package com.kesi.planit.alarm.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceJpaRepo extends JpaRepository<DeviceJpaEntity, String> {
    List<DeviceJpaEntity> findByUid(String uid);
}
