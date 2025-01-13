package com.kesi.planit.alarm.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Device {
    private String fcmToken;
    private String uid;


}
