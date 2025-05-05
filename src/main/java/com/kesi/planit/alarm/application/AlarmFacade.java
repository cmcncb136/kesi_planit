package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.presentation.dto.AlarmDataDto;
import com.kesi.planit.core.PageRequestFactory;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AlarmFacade {
    private final UserService userService;
    private final AdminAlarmService adminAlarmService;

    public ResponseEntity<Page<AlarmDataDto>> getAlarms(String uid, int page, int size) {
        User user = userService.getUserById(uid);
        Pageable pageable = PageRequestFactory.of(page, size);
        return ResponseEntity.ok(adminAlarmService.getAlarms(user, pageable));
    }
}
