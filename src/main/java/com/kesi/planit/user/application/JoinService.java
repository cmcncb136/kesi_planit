package com.kesi.planit.user.application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.core.CommonResult;
import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.presentation.dto.UserJoinRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class JoinService {
    private final UserService userService;
    private final CalendarService calendarService;
    private final FirebaseAuth auth;

    public ResponseEntity<String> join(String uid, UserJoinRequestDto joinUser) throws FirebaseAuthException {
        String email = auth.getUser(uid).getEmail();

        //사용자 캘린더 생성
        Calendar calendar = Calendar.builder().build();
        calendar = calendarService.save(calendar);

        //사용자 저장
        User user = joinUser.toModel(uid, email, "/profile.jpg", LocalDate.now(), calendar);
        user = userService.save(user);

        if(user == null)
            return ResponseEntity.status(1).body("join fail!");

        return ResponseEntity.ok("join success!");
    }
}
