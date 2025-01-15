package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.application.repository.DeviceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class DeviceRepoImpl implements DeviceRepo {
    private DeviceJpaRepo deviceJpaRepo;

    @Override
    public DeviceJpaEntity save(DeviceJpaEntity device) {
        return deviceJpaRepo.save(device);
    }

    @Override
    public DeviceJpaEntity findById(String fcmToken) {
        return deviceJpaRepo.findById(fcmToken).orElse(null);
    }

    @Override
    public void deleteById(String fcmToken) {
        deviceJpaRepo.deleteById(fcmToken);
    }
    @Override
    public List<DeviceJpaEntity> findByUid(String uid) {
        return deviceJpaRepo.findByUid(uid);
    }
}
