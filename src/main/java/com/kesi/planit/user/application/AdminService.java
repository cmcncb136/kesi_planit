package com.kesi.planit.user.application;

import com.kesi.planit.core.RoleCheck;
import com.kesi.planit.core.role.Role;
import com.kesi.planit.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;

    @RoleCheck(allowLevel = Role.MANAGER)
    public Page<User> getAllUsers(User user, Pageable pageable) {
        return userService.getUsers(pageable);
    }
}
