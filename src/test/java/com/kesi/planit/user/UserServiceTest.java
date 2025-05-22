package com.kesi.planit.user;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.group.application.repository.GroupAndUserRepo;
import com.kesi.planit.group.infrastructure.GroupAndUserJpaEntity;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.application.repository.UserRepo;
import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.infrastructure.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class) //가짜 객체를 사용하기 위해서 사용
public class UserServiceTest {
    //test 주체 객체
    UserService userService;

    @MockBean
    UserRepo userRepo;

    @MockBean
    CalendarService calendarService;

    @MockBean
    GroupAndUserRepo groupAndUserRepo;

    @BeforeEach
    void setUp() {
        userService =
                new UserService(userRepo, groupAndUserRepo, calendarService);
    }

    @Test
    @DisplayName("유저 아이디 조회")
    void getUserById(){
        //given
        UserJpaEntity userJpaEntity = UserJpaEntity.builder()
                .birth(LocalDate.of(2001, 11, 11))
                .calendarId(1L)
                .email("x@naver.com")
                .uid("x")
                .gender("Male")
                .joinDate(LocalDate.now())
                .nickname("kim")
                .imagePath("x.png")
                .build();

        Calendar calendar = Calendar.builder().id(1L).build();

        //가짜 객체에 특정 함수를 호출했을 때 특정 객체를 반환
        //userRepo.findById("x")를 호출한 경우 userJpaEntity를 반환
        Mockito.when(userRepo.findById("x")).thenReturn(userJpaEntity);
        Mockito.when(calendarService.getById(1L)).thenReturn(calendar);

        //when
        User user = userService.getById("x");

        //then
        assertThat(user.getUid()).isEqualTo("x");
        assertThat(user.getBirth()).isEqualTo(LocalDate.of(2001, 11, 11));
        assertThat(user.getEmail()).isEqualTo("x@naver.com");
        assertThat(user.getGender()).isEqualTo("Male");
        assertThat(user.getJoinDate()).isEqualTo(LocalDate.now());
        assertThat(user.getNickname()).isEqualTo("kim");

        assertThat(user.getMyCalendar().getId()).isEqualTo(calendar.getId());
    }


    @Test
    @DisplayName("유저 이메일 조회")
    void getUserByEmail(){
        //given
        UserJpaEntity userJpaEntity = UserJpaEntity.builder()
                .birth(LocalDate.of(2001, 11, 11))
                .calendarId(1L)
                .email("x@naver.com")
                .uid("x")
                .gender("Male")
                .joinDate(LocalDate.now())
                .nickname("kim")
                .imagePath("x.png")
                .build();

        Calendar calendar = Calendar.builder().id(1L).build();

        //가짜 객체에 특정 함수를 호출했을 때 특정 객체를 반환
        //userRepo.findById("x")를 호출한 경우 userJpaEntity를 반환
        Mockito.when(userRepo.findByEmail("x@naver.com")).thenReturn(userJpaEntity);
        Mockito.when(calendarService.getById(1L)).thenReturn(calendar);

        //when
        User user = userService.getByEmail("x@naver.com");

        //then
        assertThat(user.getUid()).isEqualTo("x");
        assertThat(user.getBirth()).isEqualTo(LocalDate.of(2001, 11, 11));
        assertThat(user.getEmail()).isEqualTo("x@naver.com");
        assertThat(user.getGender()).isEqualTo("Male");
        assertThat(user.getJoinDate()).isEqualTo(LocalDate.now());
        assertThat(user.getNickname()).isEqualTo("kim");

        assertThat(user.getMyCalendar().getId()).isEqualTo(calendar.getId());
    }


    @Test
    @DisplayName("존재하지 않는 유저 이메일 조회시 예외")
    void getUserByEmailFail(){
        //given

        //가짜 객체에 특정 함수를 호출했을 때 특정 객체를 반환
        Mockito.when(userRepo.findByEmail("none@naver.com")).thenThrow(NullPointerException.class);

        //when, then
        assertThatThrownBy(() ->
                userService.getByEmail("none@naver.com")
                ).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("그룹 안에 유저들 조회")
    void getByGid(){
        //given
        Long gid = 1L;

        UserJpaEntity userJpaEntity1 = UserJpaEntity.builder()
                .birth(LocalDate.of(2001, 11, 11))
                .calendarId(1L)
                .email("x1@naver.com")
                .uid("x1")
                .gender("Male")
                .joinDate(LocalDate.now())
                .nickname("kim")
                .imagePath("x.png")
                .build();

        UserJpaEntity userJpaEntity2 = UserJpaEntity.builder()
                .birth(LocalDate.of(2001, 11, 11))
                .calendarId(2L)
                .email("x2@naver.com")
                .uid("x2")
                .gender("Male")
                .joinDate(LocalDate.now())
                .nickname("kim")
                .imagePath("x.png")
                .build();

        Calendar calendar1 = Calendar.builder().id(1L).build();
        Calendar calendar2 = Calendar.builder().id(2L).build();

        //임의로 group1에 user1, user2가 들어 있는 데이터를 생성
        GroupAndUserJpaEntity groupAndUserJpaEntity1 = GroupAndUserJpaEntity.builder().gid(gid).uid(userJpaEntity1.getUid()).build();
        GroupAndUserJpaEntity groupAndUserJpaEntity2 = GroupAndUserJpaEntity.builder().gid(gid).uid(userJpaEntity2.getUid()).build();


        //가짜 객체에 특정 함수를 호출했을 때 특정 객체를 반환
        //userRepo.findById("x")를 호출한 경우 userJpaEntity를 반환
        Mockito.when(userRepo.findById("x1")).thenReturn(userJpaEntity1);
        Mockito.when(userRepo.findById("x2")).thenReturn(userJpaEntity1);

        Mockito.when(calendarService.getById(1L)).thenReturn(calendar1);
        Mockito.when(calendarService.getById(2L)).thenReturn(calendar2);

        Mockito.when(groupAndUserRepo.findByGid(gid)).thenReturn(List.of(new GroupAndUserJpaEntity[]{groupAndUserJpaEntity2, groupAndUserJpaEntity1}));

        //when
        List<User> users = userService.findByGid(gid);

        //then
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("유저 도메인 저장")
    void save(){
        //given
        Calendar calendar = Calendar.builder().id(1L).build();
        User user = User.builder()
                .myCalendar(calendar)
                .birth(LocalDate.of(2001, 11, 11))
                .email("x@naver.com")
                .uid("x")
                .gender("Male")
                .joinDate(LocalDate.now())
                .nickname("kim")
                .build();

        Mockito.when(userRepo.save(UserJpaEntity.from(user))).thenReturn(UserJpaEntity.from(user));
        //when
        User result = userService.save(user);

        //then
        assertThat(result.getMyCalendar().getId()).isEqualTo(calendar.getId());
        assertThat(result.getBirth()).isEqualTo(LocalDate.of(2001, 11, 11));
        assertThat(result.getEmail()).isEqualTo("x@naver.com");
        assertThat(result.getNickname()).isEqualTo("kim");
        assertThat(result.getMyCalendar().getId()).isEqualTo(calendar.getId());
    }

}
