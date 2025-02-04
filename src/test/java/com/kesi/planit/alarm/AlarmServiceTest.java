package com.kesi.planit.alarm;

import com.kesi.planit.alarm.application.AlarmService;
import com.kesi.planit.alarm.application.AlarmTypeService;
import com.kesi.planit.alarm.application.FCMService;
import com.kesi.planit.alarm.application.repository.AlarmRepo;
import com.kesi.planit.alarm.domain.*;
import com.kesi.planit.alarm.infrastructure.AlarmJpaEntity;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class AlarmServiceTest {
    @MockBean
    private AlarmRepo alarmRepo;
    @MockBean
    private AlarmTypeService alarmTypeService;
    @MockBean
    private UserService userService;
    @MockBean
    private FCMService fcmService;

    private AlarmService alarmService;

    private User user;
    private Calendar calendar;

    @BeforeEach
    public void setUp() {
        alarmService = new AlarmService(alarmRepo, alarmTypeService, userService, fcmService);
    }

    @BeforeEach
    public void setUpData(){
        calendar = Calendar.builder().id(100L).build();
        user = User.builder()
                .myCalendar(calendar)
                .email("x@naver.com")
                .uid("x")
                .joinDate(LocalDate.now())
                .birth(LocalDate.now())
                .nickname("test kim")
                .gender("Male")
                .imgPath("profile.jpg")
                .build();
    }

    @Test
    @DisplayName("알람 저장 테스트")
    void saveTest(){
        //given
        Alarm alarm = Alarm.builder()
                .title("test")
                .content("test content")
                .alarmType(AlarmType.GROUP)
                .user(user)
                .data(AlarmGroup.builder()
                        .alarmId(1L) //테스트 방식이 변경되면 삭제 필요
                        .gid(2L)
                        .build())
                .build();

        AlarmJpaEntity alarmJpaEntity = AlarmJpaEntity.from(alarm);
        //private filed 초기화
        ReflectionTestUtils.setField(alarmJpaEntity, "id", 1L);

        Mockito.when(alarmRepo.save(Mockito.any())).thenReturn(alarmJpaEntity);

        //Todo. 다르게 수정할 방법을 모색할 필요 있음
        Mockito.when(alarmTypeService.saveAlarmType(Mockito.any(AlarmType.class),
                        Mockito.any(AlarmData.class)))
                .thenReturn(alarm.getData()); //AlarmId가 잘 저장되는지 확인하려고 했는데 이러면 의미가 없네...

        //when
        Alarm rst = alarmService.saveAlarm(alarm);
        AlarmGroup rstAlarmData = (AlarmGroup) rst.getData();

        //then
        assertThat(rst).isNotNull();
        assertThat(rst.getId()).isEqualTo(1L);
        assertThat(rstAlarmData.getAlarmId()).isEqualTo(1L);
    }

}
