package com.kesi.planit.sessionTest;

import com.kesi.planit.user.domain.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class SessionController {

    // 회원가입 전달 방식
    // method = GET
    // UserDTO로 전달

    @RequestMapping(value = "/api/login/google", method = RequestMethod.GET)
    public ResponseEntity<Void> login(@RequestParam("code") final String code, final HttpServletRequest request){

        //todo: uid 유효성 검사
        //todo: 입력된 birthdate 분할
//        User user = User.builder()
//                .id(Long.valueOf(code))
//                .nickname("")
//                .gender(true)
//                .birthdate("")
//                .build();
        return null;
    }
}
