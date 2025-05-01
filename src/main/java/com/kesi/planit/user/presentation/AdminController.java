package com.kesi.planit.user.presentation;

import com.kesi.planit.user.application.AdminFacade;
import com.kesi.planit.user.presentation.dto.UserDto;
import com.kesi.planit.user.presentation.dto.UserWithUidDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("admin")
@RequiredArgsConstructor
@RestController
public class AdminController {
    private final AdminFacade adminService;

    @GetMapping("/users")
    public ResponseEntity<Page<UserWithUidDto>> getUsers(int page, int size, HttpServletRequest request) {
        return adminService.getAllUsers((String)request.getAttribute("uid"), page, size);
    }


}
