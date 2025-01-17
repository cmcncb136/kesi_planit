package com.kesi.planit.user.presentation;

import com.google.firebase.auth.FirebaseAuthException;
import com.kesi.planit.core.CommonResult;
import com.kesi.planit.core.FTPService;
import com.kesi.planit.user.application.JoinService;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.presentation.dto.UserDto;
import com.kesi.planit.user.presentation.dto.UserJoinRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final JoinService joinService;
    private final FTPService ftpService;

    @PostMapping("")
    public ResponseEntity<String> join(HttpServletRequest request, @RequestBody UserJoinRequestDto joinUser) throws FirebaseAuthException {
        return joinService.join(request.getAttribute("uid").toString(), joinUser);
    }

    @GetMapping("")
    public ResponseEntity<UserDto> getByUid(HttpServletRequest request) {
        UserDto result = UserDto.from(userService.getUserById(request.getAttribute("uid").toString()));
        return result != null ?
                ResponseEntity.ok(result)
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestPart("image") MultipartFile image, HttpServletRequest request) {
        String url = ftpService.uploadFileToFTP(image, (String)request.getAttribute("email"), "./", "profile.jpg");

        if(url == null)
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok(url);
    }
}
