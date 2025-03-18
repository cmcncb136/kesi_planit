package com.kesi.planit.alarm.application;

import com.google.firebase.messaging.Notification;
import com.kesi.planit.alarm.domain.*;
import com.kesi.planit.alarm.presentation.dto.AlarmDataDto;
import com.kesi.planit.alarm.presentation.dto.MessageDto;
import com.kesi.planit.alarm.presentation.dto.NotificationDto;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.domain.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AlarmService {
    private final FCMService fcmService;
    private final AlarmCRUDService alarmCRUDService;
    private final AlarmTypeService alarmTypeService;

    public ResponseEntity<AlarmDataDto> getAlarmDataDtoById(long id) {
        try{
            Alarm alarm = alarmCRUDService.getById(id);

            AlarmData alarmData = alarmTypeService.getAlarmTypeById(alarm.getId(), alarm.getAlarmType());
            return ResponseEntity.ok(AlarmDataDto.toDto(alarm, alarmData));
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<AlarmDataDto>> getAlarmDataDtoByUid(String uid){
        try{
            List<Alarm> alarmList = alarmCRUDService.getByUid(uid);

            List<AlarmDataDto> alarmDataDtoList = alarmList.stream().map(alarm ->
                    AlarmDataDto.toDto(alarm, alarmTypeService.getAlarmTypeById(alarm.getId(), alarm.getAlarmType()))).toList();

            return ResponseEntity.ok(alarmDataDtoList);

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    public void createGroupScheduleAlarm(Group group, Schedule schedule) {
        //Alarm 생성 및 저장
        List<Alarm> alarmList = new ArrayList<>();

        group.getUserList().forEach(user -> {
            if(!user.equals(group.getMaker())){ //방을 생성한 사람에게는 알림을 보내지 않음
                AlarmSchedule alarmSchedule = AlarmSchedule.builder().scheduleId(schedule.getId()).gid(group.getGid()).build();

                alarmList.add(
                        alarmCRUDService.saveAlarm(Alarm.builder()
                                .alarmType(AlarmType.SCHEDULE)
                                .title("그룹 스케줄 생성")
                                .user(user)
                                .content(group.getMaker().getNickname() + " 님께서 '" + schedule.getTitle() + "' 스케줄을 추가했습니다.")
                                .build()));

                alarmTypeService.saveAlarmType(AlarmType.SCHEDULE, alarmSchedule);
            }
        });

        //FCM으로 메시지 전송
        alarmList.forEach(alarm -> {
            AlarmData alarmData = alarmTypeService.getAlarmTypeById(alarm.getId(), alarm.getAlarmType());

            fcmService.sendNotification(
                    alarm.getUser().getUid(),
                    MessageDto.builder().
                            notificationDto(NotificationDto.toNotification(alarm))
                            .data(alarmData.toAlarmData()).build());
        });
    }

    public void createGroupAlarm(Group group) {
        //Alarm 생성 및 저장
        List<Alarm> alarmList = new ArrayList<>();

        group.getUserList().forEach(user -> {
            if(!user.equals(group.getMaker())){ //방을 생성한 사람에게는 알림을 보내지 않음
                AlarmGroup alarmGroup = AlarmGroup.builder().gid(group.getGid()).build();

                alarmList.add(
                        alarmCRUDService.saveAlarm(Alarm.builder()
                                .alarmType(AlarmType.GROUP)
                                .title("그룹 초대")
                                .user(user)
                                .content(group.getMaker().getNickname() + " 님께서 '" + group.getGroupName() + "' 그룹에 초대하였습니다.")
                                .build()));

                alarmTypeService.saveAlarmType(AlarmType.GROUP, alarmGroup);
            }
        });


        //FCM으로 메시지 전송
        alarmList.forEach(alarm -> {
            AlarmData alarmData = alarmTypeService.getAlarmTypeById(alarm.getId(), alarm.getAlarmType());

            fcmService.sendNotification(
                    alarm.getUser().getUid(),
                    MessageDto.builder().
                            notificationDto(NotificationDto.toNotification(alarm))
                            .data(alarmData.toAlarmData()).build());
        });
    }

}
