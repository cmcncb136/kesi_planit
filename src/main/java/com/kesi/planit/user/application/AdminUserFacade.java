package com.kesi.planit.user.application;

import com.kesi.planit.core.PageRequestFactory;
import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.presentation.dto.UserWithUidDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminUserFacade {
    private final UserService userService;
    private final AdminService adminService;

    public ResponseEntity<Page<UserWithUidDto>> getAllUsers(String uid, int page, int size) {
        Pageable pageable = PageRequestFactory.of(page, size);
        User user = userService.getUserById(uid);

        Page<User> users = adminService.getAllUsers(user, pageable);

        return ResponseEntity.ok(users.map(UserWithUidDto::from));
    }

}













