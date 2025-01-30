package com.kesi.planit.schedule;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.application.ScheduleService;
import com.kesi.planit.schedule.application.repository.ScheduleRepo;
import com.kesi.planit.schedule.application.repository.ScheduleSecurityRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.schedule.infrastructure.ScheduleJpaEntity;
import com.kesi.planit.user.application.UserService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class ScheduleServiceTest {
    ScheduleService scheduleService;

    @MockBean
    CalendarService calendarService;
    @MockBean
    UserService userService;

    @MockBean
    ScheduleRepo scheduleRepo;

    ScheduleJpaEntity scheduleJpaEntity;
    Schedule schedule;

    UserJpaEntity userJpaEntity;
    ArrayList<UserJpaEntity> userJpaEntityList;

    ArrayList<Calendar> calendarList;

    User user;
    ArrayList<User> userList;

    @BeforeEach
    void setDomain(){
        userJpaEntityList = new ArrayList<>();
        calendarList = new ArrayList<>();
        userList = new ArrayList<>();

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


        Calendar calendar1 = Calendar.builder().id(10L).build();
        Calendar calendar2 = Calendar.builder().id(11L).build();

        User user1 = userJpaEntity1.toModel(calendar1);
        User user2 = userJpaEntity2.toModel(calendar2);


        userJpaEntityList.add(userJpaEntity1);
        userJpaEntityList.add(userJpaEntity2);

        calendarList.add(calendar1);
        calendarList.add(calendar2);

        userList.add(user1);
        userList.add(user2);

        user = user1;
        userJpaEntity = userJpaEntity1;

        scheduleJpaEntity = ScheduleJpaEntity.builder()
                .id(1L).endDate(LocalDate.now()).startDate(LocalDate.now())
                .colorId("#FFFFFF").guestEditPermission(true).description("test").title("test")
                .startTime(LocalDateTime.now()).endTime(LocalDateTime.now().plusHours(2))
                .makerUid(user.getUid()).sourceCalendarId(user.getMyCalendar().getId()).build();
    }

    @BeforeEach
    void setup(){
        scheduleService = new ScheduleService(
                scheduleRepo, calendarService,
                userService
        );

    }

    @Test
    @DisplayName("schedule 저장")
    void save(){
        //given
        Schedule originalSchedule = scheduleJpaEntity.toModel(user, user.getMyCalendar());
        Mockito.when(scheduleRepo.save(Mockito.any(ScheduleJpaEntity.class))).thenReturn(scheduleJpaEntity);

        //when
        Schedule savedSchedule = scheduleService.save(originalSchedule);

        //then
        assertThat(savedSchedule.getDescription()).isEqualTo(originalSchedule.getDescription());
        assertThat(savedSchedule.getTitle()).isEqualTo(originalSchedule.getTitle());
        assertThat(savedSchedule.getStartDate()).isEqualTo(originalSchedule.getStartDate());
        assertThat(savedSchedule.getEndDate()).isEqualTo(originalSchedule.getEndDate());
        assertThat(savedSchedule.getStartTime()).isEqualTo(originalSchedule.getStartTime());
        assertThat(savedSchedule.getEndTime()).isEqualTo(originalSchedule.getEndTime());
    }


    @Test
    @DisplayName("스케줄 아이디 조회")
    void getById(){
        //given
        scheduleJpaEntity.toModel(user, user.getMyCalendar());

        Mockito.when(scheduleRepo.findById(1L)).thenReturn(scheduleJpaEntity);
        Mockito.when(userService.getUserById(user.getUid())).thenReturn(user);
        Mockito.when(calendarService.getById(user.getMyCalendar().getId())).thenReturn(user.getMyCalendar());


        //when
        Schedule result = scheduleService.getById(1L);

        //then
        assertThat(result.getId()).isEqualTo(1L);
    }

}
