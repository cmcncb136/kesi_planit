package com.kesi.planit.schedule;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.application.ScheduleService;
import com.kesi.planit.schedule.application.repository.ScheduleAndCalendarRepo;
import com.kesi.planit.schedule.application.repository.ScheduleRepo;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.infrastructure.ScheduleAndCalendarJpaEntity;
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
import java.util.List;
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
    GroupService groupService;


    @MockBean
    ScheduleAndCalendarRepo scheduleAndCalendarRepo;
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
                userService, groupService, scheduleAndCalendarRepo
        );




    }

    //   List.of(Schedule.ScheduleReferCalendar.builder()
    //                        .id(1L).calendar(calendarList.get(0)).access(true).build()));

    @Test
    @DisplayName("schedule 저장")
    void save(){
        //given
        Schedule originalSchedule = scheduleJpaEntity.toModel(user, user.getMyCalendar(), null);
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
    @DisplayName("schedule 생성")
    void create(){
        //given
        //캘린더 참조 객체 생성
        Schedule.ScheduleReferCalendar scheduleReferCalendar = Schedule.ScheduleReferCalendar.builder()
                .calendar(user.getMyCalendar()).build();

        //스케줄 객체 생성
        Schedule originalSchedule = scheduleJpaEntity.toModel(user, user.getMyCalendar(), List.of(
                scheduleReferCalendar
        ));

        //스케줄 캘린더 관계 객체 생성
        ScheduleAndCalendarJpaEntity scheduleAndCalendarJpaEntity = ScheduleAndCalendarJpaEntity.builder()
                .scheduleId(originalSchedule.getId()).calendarId(user.getMyCalendar().getId()).build();

        ScheduleService spyScheduleService = Mockito.spy(scheduleService);
        Mockito.doReturn(originalSchedule).when(spyScheduleService).save(originalSchedule);
        Mockito.when(scheduleAndCalendarRepo.save(Mockito.any(ScheduleAndCalendarJpaEntity.class)))
                .thenReturn(scheduleAndCalendarJpaEntity);

        //when
        Schedule result = spyScheduleService.createSchedule(originalSchedule);

        //then
        assertThat(result.getCalendars().size()).isEqualTo(1);
        assertThat(result.getCalendars().get(0).getCalendar().getId()).isEqualTo(scheduleReferCalendar.getCalendar().getId());
    }

    @Test
    @DisplayName("스케줄 아이디 조회")
    void getById(){
        //given
        scheduleJpaEntity.toModel(user, user.getMyCalendar(), new ArrayList<>());

        Mockito.when(scheduleRepo.findById(1L)).thenReturn(scheduleJpaEntity);
        Mockito.when(userService.getUserById(user.getUid())).thenReturn(user);
        Mockito.when(calendarService.getById(user.getMyCalendar().getId())).thenReturn(user.getMyCalendar());

        Mockito.when(scheduleAndCalendarRepo.findByScheduleId(1L)).thenReturn(new ArrayList<>());

        //when
        Schedule result = scheduleService.getById(1L);

        //then
        assertThat(result.getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("그룹에 스케줄 추가")
    void addGroupSchedule(){
        //given
        Group group = Group.builder().groupCalendar(calendarList.get(0)).groupName("test")
                .maker(user).gid(1L)
                .users(Map.of(user.getUid(), Group.GroupInUser.builder().allowedSecurityLevel(3).user(user).build()))
                .build();


        Mockito.when(groupService.getByCalendarId(calendarList.get(0).getId()))
                .thenReturn(group);

    }
}
