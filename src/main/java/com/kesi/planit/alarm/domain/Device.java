package com.kesi.planit.alarm.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Device {
    private final String fcmToken;
    private final String uid;
}
