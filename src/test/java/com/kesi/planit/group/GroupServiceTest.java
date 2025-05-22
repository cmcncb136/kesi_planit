package com.kesi.planit.group;

import com.kesi.planit.alarm.application.AlarmService;
import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.application.repository.GroupAndUserRepo;
import com.kesi.planit.group.application.repository.GroupRepo;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.group.domain.GroupUserMap;
import com.kesi.planit.group.infrastructure.GroupAndUserJpaEntity;
import com.kesi.planit.group.infrastructure.GroupJpaEntity;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
public class GroupServiceTest {
    //Test 주체 객체
    GroupService groupService;

    @MockBean
    UserService userService;

    @MockBean
    CalendarService calendarService;

    @MockBean
    GroupRepo groupRepo;

    @MockBean
    GroupAndUserRepo groupAndUserRepo;

    @MockBean
    AlarmService alarmService;

    UserJpaEntity userJpaEntity;
    ArrayList<UserJpaEntity> userJpaEntityList;

    ArrayList<Calendar> calendarList;

    User user;
    ArrayList<User> userList;

    @BeforeEach
    void setUser(){
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
    }


    @BeforeEach
    void setup(){
        groupService = new GroupService(groupRepo, groupAndUserRepo, calendarService, userService, alarmService);
    }

    @Test
    @DisplayName("그룹 gid 조회")
    void getById(){
        //given
        //group 안에 있는 test용 user 객체 2개 생성
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

        //user들이 사용할 calender 각각 1개 --> 2
        //group에서 사용할 calender 1개 --> 1
        Calendar calendar1 = Calendar.builder().id(1L).build();
        Calendar calendar2 = Calendar.builder().id(2L).build();
        Calendar calendar3 = Calendar.builder().id(3L).build();

        GroupJpaEntity groupJpaEntity = GroupJpaEntity.builder()
                .groupName("test").makerUid("x1").calendarId(3L).gid(1L).build();

        //user가 group안에 있는 정보를 생성
        GroupAndUserJpaEntity groupAndUserJpaEntity1 = GroupAndUserJpaEntity.builder()
                .gid(groupJpaEntity.getGid())
                .uid(userJpaEntity1.getUid())
                .build();
        GroupAndUserJpaEntity groupAndUserJpaEntity2 = GroupAndUserJpaEntity.builder()
                .gid(groupJpaEntity.getGid())
                .uid(userJpaEntity2.getUid())
                .build();


        Mockito.when(groupRepo.findById(groupJpaEntity.getGid())).thenReturn(groupJpaEntity);

        Mockito.when(userService.getById(userJpaEntity1.getUid())).thenReturn(userJpaEntity1.toModel(calendar1));
        Mockito.when(userService.getById(userJpaEntity2.getUid())).thenReturn(userJpaEntity2.toModel(calendar2));

        Mockito.when(calendarService.getById(calendar3.getId())).thenReturn(calendar3);

        Mockito.when(groupAndUserRepo.findByGid(groupJpaEntity.getGid()))
                .thenReturn(List.of(new GroupAndUserJpaEntity[]{groupAndUserJpaEntity1, groupAndUserJpaEntity2}));

        //when
        Group group = groupService.getById(groupJpaEntity.getGid());

        //then
        assertThat(group.getGid()).isEqualTo(groupJpaEntity.getGid());
        assertThat(group.getGroupName()).isEqualTo(groupJpaEntity.getGroupName());
        assertThat(group.getUsers().size()).isEqualTo(2);
        assertThat(group.getUsers().get(userJpaEntity1.getUid())).isNotNull();
        assertThat(group.getUsers().get(userJpaEntity2.getUid())).isNotNull();
    }

    @Test
    @DisplayName("그룹 리스트를 uid로 조회")
    void getByUid(){
        GroupService spyGroupService = Mockito.spy(groupService);

        //given
        GroupJpaEntity groupJpaEntity1 = GroupJpaEntity.builder()
                .groupName("test1")
                .makerUid(user.getUid())
                .gid(1L)
                .calendarId(1L)
                .build();

        GroupJpaEntity groupJpaEntity2 = GroupJpaEntity.builder()
                .groupName("test2")
                .makerUid(user.getUid())
                .gid(2L)
                .calendarId(2L)
                .build();

        Calendar calendar1 = Calendar.builder().id(1L).build();
        Calendar calendar2 = Calendar.builder().id(2L).build();

        GroupAndUserJpaEntity groupAndUserJpaEntity1 = GroupAndUserJpaEntity.builder()
                .gid(groupJpaEntity1.getGid()).uid(user.getUid()).build();

        GroupAndUserJpaEntity groupAndUserJpaEntity2 = GroupAndUserJpaEntity.builder()
                .gid(groupJpaEntity2.getGid()).uid(user.getUid()).build();

        HashMap<String, Group.GroupInUser> groupInUserHashMap1 = new HashMap<>();
        HashMap<String, Group.GroupInUser> groupInUserHashMap2 = new HashMap<>();

        groupInUserHashMap1.put(user.getUid(), groupAndUserJpaEntity1.mappingGroupToGroupInUser(user));
        groupInUserHashMap2.put(user.getUid(), groupAndUserJpaEntity2.mappingGroupToGroupInUser(user));

        Group group1 = groupJpaEntity1.toModel(new GroupUserMap(groupInUserHashMap1), calendar1);
        Group group2 = groupJpaEntity2.toModel(new GroupUserMap(groupInUserHashMap2), calendar2);

        Mockito.when(groupAndUserRepo.findByUid(user.getUid())).thenReturn(
                List.of(new GroupAndUserJpaEntity[]{groupAndUserJpaEntity1, groupAndUserJpaEntity2}));

        Mockito.doReturn(group1).when(spyGroupService).getById(groupJpaEntity1.getGid());
        Mockito.doReturn(group2).when(spyGroupService).getById(groupJpaEntity2.getGid());

        //when
        List<Group> result = spyGroupService.getByUid(user.getUid());

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).containsExactlyInAnyOrder(group1, group2); // 결과 리스트 검증
    }
}

