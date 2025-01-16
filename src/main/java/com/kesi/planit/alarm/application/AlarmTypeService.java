package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.application.repository.AlarmTypeRepo;
import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.domain.AlarmType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlarmTypeService {
    private final List<AlarmTypeData> alarmTypeRepoList;

    private AlarmTypeData findAlarmType(AlarmType alarmType){
        return alarmTypeRepoList.stream().filter(it ->
                it.getAlarmType() == alarmType).findFirst().orElseThrow();
    }


    public AlarmData getAlarmTypeById(Long alarmId, AlarmType alarmType) {
        AlarmTypeData alarmTypeRepo = findAlarmType(alarmType);

        return alarmTypeRepo.getByAlarmId(alarmId);
    }


    public AlarmData saveAlarmType(AlarmType alarmType, AlarmData alarmData) {
        AlarmTypeData alarmTypeRepo = findAlarmType(alarmType);

        return  alarmTypeRepo.save(alarmData);
    }
}
