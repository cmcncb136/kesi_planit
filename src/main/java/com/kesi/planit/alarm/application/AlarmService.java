package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.application.repository.AlarmRepo;
import com.kesi.planit.alarm.domain.*;
import com.kesi.planit.alarm.infrastructure.AlarmJpaEntity;
import com.kesi.planit.alarm.presentation.dto.AlarmDataDto;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AlarmService {
    private final AlarmRepo alarmRepo;
    private final AlarmTypeService alarmTypeService;
    private final UserService userService;
    private final FCMService fcmService;

    private Alarm getById(long id) {
        AlarmJpaEntity alarmJpaEntity = alarmRepo.findById(id);

        User user = userService.getUserById(alarmJpaEntity.getUid());
        AlarmData alarmData = alarmTypeService.getAlarmTypeById(alarmJpaEntity.getId(), alarmJpaEntity.getAlarmType());

        return alarmJpaEntity.toModel(user, alarmData);
    }

    private List<Alarm> getByUid(String uid){
        User user = userService.getUserById(uid);

        return alarmRepo.findByUid(user.getUid()).stream().map(alarmJpaEntity ->
            alarmJpaEntity.toModel(user,
                    alarmTypeService.getAlarmTypeById(alarmJpaEntity.getId(), alarmJpaEntity.getAlarmType()))
        ).toList();
    }


    public ResponseEntity<AlarmDataDto> getAlarmDataDtoById(long id) {
        try{
            Alarm alarm = getById(id);
            return ResponseEntity.ok(AlarmDataDto.toDto(alarm));
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<AlarmDataDto>> getAlarmDataDtoByUid(String uid){
        try{
            List<Alarm> alarmList = getByUid(uid);
            return
                    ResponseEntity.ok(alarmList.stream()
                            .map(AlarmDataDto::toDto).toList());
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    public void createGroupScheduleAlarm(Group group, Schedule schedule) {
        //Alarm 생성 및 저장
        List<Alarm> alarmList = new ArrayList<>();

        group.getUsers().values().forEach(user -> {
            if(!user.getUser().getUid().equals(group.getMaker().getUid())){ //방을 생성한 사람에게는 알림을 보내지 않음
                AlarmSchedule alarmSchedule = AlarmSchedule.builder().scheduleId(schedule.getId()).gid(group.getGid()).build();

                alarmList.add(
                        saveAlarm(Alarm.builder()
                                .alarmType(AlarmType.SCHEDULE)
                                .title("그룹 스케줄 생성")
                                .user(user.getUser())
                                .content(group.getMaker().getNickname() + " 님께서 '" + schedule.getTitle() + "' 스케줄을 추가했습니다.")
                                .data(alarmSchedule)
                                .build()));
            }
        });

        //FCM으로 메시지 전송
        alarmList.forEach(alarm -> {
            fcmService.sendNotification(alarm.getUser().getUid(), alarm.toMessageDto());
        });
    }

    public void createGroupAlarm(Group group) {
        //Alarm 생성 및 저장
        List<Alarm> alarmList = new ArrayList<>();

        group.getUsers().values().forEach(user -> {
            if(!user.getUser().getUid().equals(group.getMaker().getUid())){ //방을 생성한 사람에게는 알림을 보내지 않음
                AlarmGroup alarmGroup = AlarmGroup.builder().gid(group.getGid()).build();

               alarmList.add(
                       saveAlarm(Alarm.builder()
                        .alarmType(AlarmType.GROUP)
                        .title("그룹 초대")
                        .user(user.getUser())
                        .content(group.getMaker().getNickname() + " 님께서 '" + group.getGroupName() + "' 그룹에 초대하였습니다.")
                        .data(alarmGroup)
                        .build()));
            }
        });

        //FCM으로 메시지 전송
        alarmList.forEach(alarm -> {
            fcmService.sendNotification(alarm.getUser().getUid(), alarm.toMessageDto());
        });
    }

    //알림 아이디는 저장할 때 자동으로 매칭시켜줌.
    public Alarm saveAlarm(Alarm alarm) {
        //Alarm 저장
        AlarmJpaEntity alarmJpaEntity = alarmRepo.save(AlarmJpaEntity.from(alarm));

        //AlarmData 저장
        AlarmData alarmData = alarm.getData();
        alarmData.setAlarmId(alarmJpaEntity.getId());
        alarmData = alarmTypeService.saveAlarmType(alarm.getAlarmType(), alarmData);

        //반환
        return alarmJpaEntity.toModel(alarm.getUser(), alarmData);
    }
}



















