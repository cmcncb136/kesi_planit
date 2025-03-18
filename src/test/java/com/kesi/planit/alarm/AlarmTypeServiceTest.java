package com.kesi.planit.alarm;

import com.kesi.planit.alarm.application.*;
import com.kesi.planit.alarm.application.repository.AlarmRepo;
import com.kesi.planit.alarm.domain.AlarmType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class AlarmTypeServiceTest {
    @MockBean
    private AlarmGroupService alarmGroupService;

    @MockBean
    private AlarmBasicService alarmBasicService;

    @MockBean
    private AlarmScheduleService alarmScheduleService;

    private List<AlarmTypeData> alarmTypeDataList;

    private AlarmTypeService alarmTypeService;

    @BeforeEach
    public void setUp() {
        alarmTypeDataList = List.of(
                alarmBasicService, alarmGroupService, alarmScheduleService);

        Mockito.when(alarmGroupService.getAlarmType()).thenReturn(AlarmType.GROUP);
        Mockito.when(alarmBasicService.getAlarmType()).thenReturn(AlarmType.BASIC);
        Mockito.when(alarmScheduleService.getAlarmType()).thenReturn(AlarmType.SCHEDULE);

        alarmTypeService = new AlarmTypeService(alarmTypeDataList);
    }


}
