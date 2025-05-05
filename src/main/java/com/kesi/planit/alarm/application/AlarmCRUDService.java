package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.application.repository.AlarmRepo;
import com.kesi.planit.alarm.domain.*;
import com.kesi.planit.alarm.infrastructure.AlarmJpaEntity;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlarmCRUDService {
    private final AlarmRepo alarmRepo;
    private final UserService userService;

    public Alarm getById(long id) {
        AlarmJpaEntity alarmJpaEntity = alarmRepo.findById(id);

        User user = userService.getUserById(alarmJpaEntity.getUid());

        return alarmJpaEntity.toModel(user);
    }

    public List<Alarm> getByUid(String uid){
        User user = userService.getUserById(uid);

        //Todo. 기간 조회로 변경해야 됨
        return alarmRepo.findByUid(user.getUid()).stream().map(alarmJpaEntity ->
            alarmJpaEntity.toModel(user)
        ).toList();
    }

    //알림 아이디는 저장할 때 자동으로 매칭시켜줌.
    public Alarm saveAlarm(Alarm alarm) {
        //Alarm 저장
        AlarmJpaEntity alarmJpaEntity = alarmRepo.save(AlarmJpaEntity.from(alarm));

        //반환
        return alarmJpaEntity.toModel(alarm.getUser());
    }

    public Page<Alarm> getAlarms(Pageable pageable) {
        return alarmRepo.findAll(pageable).map(it -> it.toModel(userService.getUserById(it.getUid())));
    }
}



















