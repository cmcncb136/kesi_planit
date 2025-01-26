package com.kesi.planit.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.core.CommonResult;
import com.kesi.planit.user.application.JoinService;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.presentation.dto.UserJoinRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
public class JoinServiceTest {
    JoinService joinService;

    @MockBean
    UserService userService;

    @MockBean
    CalendarService calendarService;

    @MockBean
    FirebaseAuth auth;

    @MockBean //체인으로 연결된 함수에 경우 체인에서 반환되는 가짜 객체도 필요함
    UserRecord userRecord;

    @BeforeEach
    public void setup(){
        joinService = new JoinService(userService, calendarService, auth);
    }

    @Test
    @DisplayName("유저 회원가입")
    public void join() throws FirebaseAuthException {
        //given
        String uid = "x";

        Calendar calendar = Calendar.builder().build();
        Calendar savedCalendar = Calendar.builder().id(1L).build();

        UserJoinRequestDto joinRequestDto = UserJoinRequestDto.builder()
                .birth(LocalDate.of(2001,11,11).toString())
                .gender("Male")
                .nickname("kim")
                .build();

        User user = joinRequestDto.toModel(uid, "x@naver.com", "basic.jpg", LocalDate.now(), calendar);

        //Calender, User와 같이 함수 매개변수로 들어가는 객체가 테스트 하려고하는 함수 내부에서 생성된다면
        //특정 클래스 형태와 맞다면 다음 값을 반환하다라는 식으로 고친다.
        Mockito.when(calendarService.save(Mockito.any(Calendar.class))).thenReturn(savedCalendar);
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);


        //Mockito.when(auth.getUser(uid).getEmail()).thenReturn("x@naver.com");
        // 위와 같이 체인 형태에 경우(중간에 반환되는 객체게 일반적인 객체가 아니라면)
        // 중간에 반환되는 가짜 객체를 만들고 끊어서  작성해야 한다.
        //userRecord 가짝 객체의 특정 함수가 실행될 때 어떤 값을 반환할지 설정한다.
        Mockito.when(userRecord.getEmail()).thenReturn("x@naver.com");
        Mockito.when(auth.getUser(uid)).thenReturn(userRecord);

        //when
        ResponseEntity<String> response = joinService.join(uid, joinRequestDto);

        //then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

}
