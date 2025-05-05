package com.kesi.planit.schedule.application;

import com.kesi.planit.core.PageRequestFactory;
import com.kesi.planit.schedule.presentation.dto.ScheduleSourceDto;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleFacade {
    private final UserService userService;
    private final AdminScheduleService adminScheduleService;

    public ResponseEntity<Page<ScheduleSourceDto>> getScheduleSources(String uid, int page, int size) {
        User user = userService.getUserById(uid);
        Pageable pageable = PageRequestFactory.of(page, size);
        return ResponseEntity.ok(adminScheduleService.getScheduleSources(user, pageable));
    }

}
