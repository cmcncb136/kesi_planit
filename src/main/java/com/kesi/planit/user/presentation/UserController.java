package com.kesi.planit.user.presentation;

import com.google.firebase.auth.FirebaseAuthException;
import com.kesi.planit.core.FTPService;
import com.kesi.planit.user.application.JoinService;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.presentation.dto.UserDto;
import com.kesi.planit.user.presentation.dto.UserJoinRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    private final UserService userService;
    private final JoinService joinService;
    private final FTPService ftpService;

    // 회원가입
    @Operation(
            summary = "회원 가입",
            description = "Firebase UID 기반으로 사용자를 가입시킵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "가입 성공"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    @PostMapping("")
    public ResponseEntity<String> join(HttpServletRequest request, @RequestBody UserJoinRequestDto joinUser) throws FirebaseAuthException {
        return joinService.join(request.getAttribute("uid").toString(), joinUser);
    }

    // 사용자 조회
    @Operation(
            summary = "사용자 조회",
            description = "Firebase UID로 현재 로그인된 사용자를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "404", description = "사용자 없음")
            }
    )
    @GetMapping("")
    public ResponseEntity<UserDto> getByUid(HttpServletRequest request) {
        UserDto result = UserDto.from(userService.getById(request.getAttribute("uid").toString()));
        log.info(result.toString());
        return result != null ?
                ResponseEntity.ok(result)
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "프로필 이미지 업로드",
            description = "이미지 파일을 업로드하고 FTP 서버에 저장된 URL을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "업로드 성공"),
                    @ApiResponse(responseCode = "500", description = "업로드 실패")
            }
    )
    @PostMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestPart("image") MultipartFile image, HttpServletRequest request) {
        String url = ftpService.uploadFileToFTP(image, (String)request.getAttribute("email"), "./", "profile.jpg");

        if(url == null)
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok(url);
    }
}
