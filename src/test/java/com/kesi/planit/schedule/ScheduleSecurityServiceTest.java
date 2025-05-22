package com.kesi.planit.schedule;

import com.kesi.planit.alarm.application.AlarmService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.group.domain.GroupUserMap;
import com.kesi.planit.schedule.application.ScheduleSecurityService;
import com.kesi.planit.schedule.application.ScheduleSourceService;
import com.kesi.planit.schedule.application.repository.ScheduleSecurityRepo;
import com.kesi.planit.schedule.domain.ScheduleSource;
import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.schedule.infrastructure.ScheduleSecurityEntity;
import com.kesi.planit.schedule.presentation.dto.RequestPersonalScheduleDto;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class ScheduleSecurityServiceTest {
    ScheduleSecurityService  scheduleSecurityService;

    @MockBean
    UserService userService;

    @MockBean
    GroupService groupService;

    @MockBean
    AlarmService alarmService;

    @MockBean
    ScheduleSourceService scheduleService;

    @MockBean
    ScheduleSecurityRepo scheduleSecurityRepo;

    @MockBean
    RequestPersonalScheduleDto requestPersonalScheduleDto;

    private User user;
    private Calendar calendar;
    private ScheduleSource schedule;

    private Group group;
    private Calendar groupCalendar;
    private ScheduleSource groupSchedule;


    @BeforeEach
    public void setup() {
        scheduleSecurityService = new ScheduleSecurityService(
                scheduleSecurityRepo,
                scheduleService,
                userService,
                groupService,
                alarmService
        );

        calendar = Calendar.builder().id(200L).build();
        groupCalendar = Calendar.builder().id(201L).build();

        user = User.builder()
                .uid("x").email("x@naver.com").birth(LocalDate.now())
                .gender("Male").imgPath("profile.jpg").nickname("kjw").myCalendar(calendar)
                .build();


        schedule = ScheduleSource.builder()
                .id(200L)
                .title("Schedule Test")
                .description("Schedule Test")
                .color(Color.BLACK).maker(user)
                .sourceCalendar(calendar)
                .startTime(LocalTime.now()).endTime(LocalTime.now())
                .endDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1))
                .build();


        HashMap<String, Group.GroupInUser> users = new HashMap<>();
        users.put(user.getUid(), Group.GroupInUser.builder()
                .user(user).id(100L).allowedSecurityLevel(SecurityLevel.MEDIUM).build());

        group = Group.builder()
                .gid(200L)
                .maker(user)
                .groupCalendar(groupCalendar)
                .groupName("Group Test")
                .users(new GroupUserMap(users))
                .build();

        groupSchedule = ScheduleSource.builder()
                .id(201L)
                .title("Group Schedule Test")
                .description("Group Schedule Test")
                .color(Color.WHITE).maker(user)
                .sourceCalendar(groupCalendar)
                .startTime(LocalTime.now()).endTime(LocalTime.now())
                .endDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1))
                .build();
    }

    @Test
    @DisplayName("그룹에 포함되지 않은 유저가 스케줄을 추가할 시 거부 테스트")
    public void setGroupScheduleSecurityDeniedTest() {
        //given
        User anonymousUser = User.builder() //그룹에 속하지 않은 유저
                .email("anonymous@naver.com").uid("anonymous").myCalendar(null)
                .joinDate(LocalDate.now()).nickname("anonymous").birth(LocalDate.now())
                .gender("Male").imgPath("profile.jpg")
                .build();

        Mockito.when(scheduleService.getById(groupSchedule.getId())).thenReturn(groupSchedule);
        Mockito.when(userService.getById(anonymousUser.getUid())).thenReturn(anonymousUser);
        Mockito.when(groupService.getByCalendarId(groupCalendar.getId())).thenReturn(group);

        //when
        ResponseEntity<String> rst = scheduleSecurityService.setGroupScheduleSecurity(anonymousUser.getUid(), groupSchedule.getId(), SecurityLevel.MEDIUM);

        //then
        assertThat(rst.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("그룹에 생성된 스케줄 보안등급 설정 테스트")
    public void setGroupScheduleSecurityTest() {
        //given
        ScheduleSecurity scheduleSecurity = ScheduleSecurity.builder()
                .id(1L)
                .securityLevel(SecurityLevel.MEDIUM)
                .schedule(groupSchedule)
                .user(user)
                .build();

        Mockito.when(scheduleService.getById(groupSchedule.getId())).thenReturn(groupSchedule);
        Mockito.when(userService.getById(user.getUid())).thenReturn(user);
        Mockito.when(groupService.getByCalendarId(groupCalendar.getId())).thenReturn(group);
        Mockito.when(scheduleSecurityRepo.save(Mockito.any(ScheduleSecurityEntity.class))).
                thenReturn(ScheduleSecurityEntity.from(scheduleSecurity));

        //when
        ResponseEntity<String> rst =
                scheduleSecurityService.setGroupScheduleSecurity(user.getUid(), groupSchedule.getId(), SecurityLevel.MEDIUM);

        //then
        assertThat(rst.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    @DisplayName("개인 스케줄 추가 테스트")
    public void addPersonalScheduleTest() {
        //given
        ScheduleSecurity scheduleSecurity = ScheduleSecurity.builder()
                .securityLevel(SecurityLevel.MEDIUM)
                .id(1L)
                .user(user)
                .schedule(schedule)
                .build();

        Mockito.when(userService.getById(user.getUid())).thenReturn(user);
        Mockito.when(scheduleService.save(Mockito.any(ScheduleSource.class))).thenReturn(schedule);
        //Mockito.when(requestPersonalScheduleDto.toModel(user)).thenReturn(schedule);
        Mockito.when(requestPersonalScheduleDto.getSecurityLevel()).thenReturn(scheduleSecurity.getSecurityLevel());

        Mockito.when(scheduleSecurityRepo.save(Mockito.any(ScheduleSecurityEntity.class)))
                .thenReturn(ScheduleSecurityEntity.from(scheduleSecurity));

        //when
//        ResponseEntity<Long> rst = scheduleSecurityService.addPersonalSchedule(user.getUid(), requestPersonalScheduleDto);
//
//        //then
//        assertThat(rst.getStatusCode().value()).isEqualTo(200);
//        assertThat(rst.getBody()).isEqualTo(String.valueOf(scheduleSecurity.getId()));
    }

    @Test
    @DisplayName("유저와 연결된 스케줄 월별 조회 테스트")
    public void getScheduleSecurityMonthByUidTest(){
        //given
        List<ScheduleSecurityEntity> scheduleSecurityEntityList = new ArrayList<ScheduleSecurityEntity>();
        List<ScheduleSource> scheduleList = new ArrayList<>();
        LocalDate date = LocalDate.now();
        LocalDate start = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
        LocalDate end = LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth());

        for(int i = 1; i <= 10; i++){
            scheduleList.add(ScheduleSource.builder()
                    .color(Color.BLACK)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .startTime(LocalTime.now())
                    .endTime(LocalTime.now())
                    .maker(user)
                    .sourceCalendar(calendar)
                    .title("title")
                    .description("title description")
                    .guestEditPermission(true)
                    .id((long) i)
                    .build());

            scheduleSecurityEntityList.add(ScheduleSecurityEntity.builder()
                    .id((long)i).securityLevel(SecurityLevel.MEDIUM).scheduleId((long)i)
                    .uid(user.getUid()).build());

        }

        scheduleList.forEach(it ->
                Mockito.when(scheduleService.getById(it.getId())).thenReturn(schedule)
        );

        Mockito.when(userService.getById(user.getUid())).thenReturn(user);
        Mockito.when(scheduleSecurityRepo.findSchedulesUidAndWithinDateRange(user.getUid(), start, end)).thenReturn(scheduleSecurityEntityList);

        //when
        List<ScheduleSecurity> rst = scheduleSecurityService.getScheduleSecurityMonthByUid(date, user.getUid());

        //then
        assertThat(rst.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("그룹에 있는 유저들 스케줄 월별 조회 테스트")
    public void getGroupUserScheduleAndMonthTest(){
        //given
        LocalDate date = LocalDate.now();
        LocalDate start = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
        LocalDate end = LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth());

        List<Calendar> calendars = new ArrayList<>(); //캘린더 생성
        List<User> userList = new ArrayList<>();// 유저들 생성

        for(int i = 1; i <= 4; i++) { //멤버 4명을 추가
            List<ScheduleSecurity> scheduleSecurityList = new ArrayList<>(); //스케줄 시큐리티 생성

            Calendar calendar = Calendar.builder().id((long) i).build();
            calendars.add(calendar);

            User user = User.builder()
                    .gender("Male").birth(LocalDate.now())
                    .nickname("kim").uid("s" + i).joinDate(LocalDate.now())
                    .email("s" + i + "@naver.com").myCalendar(calendar).build();
            userList.add(user);

            group.getUsers().put(user.getUid(),
                    Group.GroupInUser.builder()
                            .user(user).allowedSecurityLevel(SecurityLevel.MEDIUM)
                            .build());

            for(int j = 0; j < 10; j++){
                ScheduleSource schedule = ScheduleSource.builder() //스케줄 생성
                        .color(Color.BLACK)
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .startTime(LocalTime.now())
                        .endTime(LocalTime.now()).maker(user)
                        .sourceCalendar(calendar).title("title")
                        .description("title description")
                        .guestEditPermission(true).id((long) i * 10 + j)
                        .build();

                scheduleSecurityList.add(ScheduleSecurity.builder()
                        .user(user).securityLevel(SecurityLevel.MEDIUM)
                        .id((long) i * 10 + j).schedule(schedule).build());

                Mockito.when(scheduleService.getById(schedule.getId())).thenReturn(schedule);
            }

            Mockito.when(scheduleSecurityRepo.findSchedulesUidAndWithinDateRange(user.getUid(), start, end))
                    .thenReturn(scheduleSecurityList.stream().map(ScheduleSecurityEntity::from).toList());

            Mockito.when(userService.getById(user.getUid())).thenReturn(user);
        }
        Mockito.when(groupService.getById(group.getGid())).thenReturn(group);

        //when
//        ResponseEntity<List<GroupUserScheduleDto>> rst = scheduleSecurityService.getGroupUserSchedulesInMonth(date.toString(), "s1", group.getGid());

        //then
//        assertThat(rst.getStatusCode().value()).isEqualTo(200);
//        assertThat(rst.getBody().size()).isEqualTo(10 * (userList.size() - 1));
    }
}
