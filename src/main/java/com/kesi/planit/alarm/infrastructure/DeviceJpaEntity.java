package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.domain.Device;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "device")
public class DeviceJpaEntity {
    @Id
    @Column(length = 150)
    private String fcmToken;

    private String uid;

    @Builder
    public DeviceJpaEntity(String fcmToken, String uid) {
        this.fcmToken = fcmToken;
        this.uid = uid;
    }

    public Device toModel(){
        return Device.builder().fcmToken(fcmToken).uid(uid).build();
    }

    public static DeviceJpaEntity from(Device device){
        return DeviceJpaEntity.builder()
                .fcmToken(device.getFcmToken())
                .uid(device.getUid())
                .build();
    }

}
