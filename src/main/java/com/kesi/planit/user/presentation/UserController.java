package com.kesi.planit.user.presentation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.kesi.planit.core.CommonResult;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.presentation.dto.UserJoinRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public CommonResult join(HttpServletRequest request, @RequestBody UserJoinRequestDto joinUser) throws FirebaseAuthException {
        return userService.join(request.getAttribute("uid").toString(), joinUser);
    }
}
