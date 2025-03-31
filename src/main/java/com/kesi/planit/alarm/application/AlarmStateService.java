package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.domain.*;
import com.kesi.planit.alarm.presentation.dto.AlarmDataDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AlarmStateService {
    private final AlarmCRUDService alarmCRUDService;
    private final AlarmTypeService alarmTypeService;

    //특정 알림 처리 완료
    private void processAlarm(Alarm alarm) {
        alarm.process();
        alarmCRUDService.saveAlarm(alarm);
    }

    public void processGroupSchedule(String uid, Long scheduleId) {
        List<Alarm> alarmList = alarmCRUDService.getByUid(uid);
        alarmList.forEach(alarm -> {
            if(alarm.getAlarmType() == AlarmType.SCHEDULE) {
                AlarmSchedule alarmSchedule = (AlarmSchedule) alarmTypeService.getAlarmTypeByAlarm(alarm);
                if(alarmSchedule.getScheduleId().equals(scheduleId)){
                    processAlarm(alarm);
                    //Todo. return
                }
            }
        });
    }


    //특정 그룹에 처리 안된 알림 조회
    public ResponseEntity<List<AlarmDataDto>> getPendingAlarmInGroup(String uid, Long gid) {
        List<Alarm> alarmList = alarmCRUDService.getByUid(uid);

        ArrayList<AlarmDataDto> alarmDataDtoArrayList = new ArrayList<>();
        alarmList.forEach(alarm -> {
            AlarmData alarmData = alarmTypeService.getAlarmTypeByAlarm(alarm);

            //그룹과 연관되어 있는지 확인
            if(alarmData.relationType(AlarmType.GROUP) && alarm.getAlarmState() == AlarmStatus.PENDING){
                GroupRelated groupRelated = (GroupRelated) alarmData;

                if(groupRelated.getGid().equals(gid))
                    alarmDataDtoArrayList.add(AlarmDataDto.toDto(alarm, alarmData));
            }
        });

        return new ResponseEntity<>(alarmDataDtoArrayList, HttpStatus.OK);
    }
}
















