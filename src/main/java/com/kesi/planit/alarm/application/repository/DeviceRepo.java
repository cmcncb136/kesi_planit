package com.kesi.planit.alarm.application.repository;

import com.kesi.planit.alarm.infrastructure.DeviceJpaEntity;

import java.util.List;

public interface DeviceRepo {
    DeviceJpaEntity save(DeviceJpaEntity device);
    DeviceJpaEntity findById(String fcmToken);
    void deleteById(String fcmToken);
    List<DeviceJpaEntity> findByUid(String uid);
}
