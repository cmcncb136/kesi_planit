package com.kesi.planit.test;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor
@Controller
@ResponseBody
public class FirebaseAuthController {
    private GoogleTokenService googleTokenService;

    @PostMapping("/auth/")
    public Boolean auth(@RequestParam("code") String code) throws FirebaseAuthException {
        System.out.println("code : " + code);

        //FirebaseToken decodeToken = FirebaseAuth.getInstance().verifyIdToken(token);
        
        //String uid = decodeToken.getUid();

        if(code != null) {
            googleTokenService.exchangeCodeForAccessToken(code);
            return Boolean.TRUE;
        }else
            return Boolean.FALSE;
    }
}
