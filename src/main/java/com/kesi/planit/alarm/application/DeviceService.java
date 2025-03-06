package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.application.repository.DeviceRepo;
import com.kesi.planit.alarm.domain.Device;
import com.kesi.planit.alarm.infrastructure.DeviceJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeviceService {
    private final DeviceRepo deviceRepo;

    public Device save(Device device) {
        return deviceRepo.save(DeviceJpaEntity.from(device)).toModel();
    }

    public Device getByFcmToken(String fcmToken) {
        return deviceRepo.findById(fcmToken).toModel();
    }

    public List<Device> getByUid(String uid){
        return deviceRepo.findByUid(uid).stream().map(DeviceJpaEntity::toModel).toList();
    }

    public void deleteByFcmToken(String fcmToken) {
        deviceRepo.deleteById(fcmToken);
    }


    //Todo. 추후 UID로 삭제하는 기능도 추가
}
