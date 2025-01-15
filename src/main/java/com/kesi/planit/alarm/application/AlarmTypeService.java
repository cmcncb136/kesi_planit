package com.kesi.planit.alarm.application;

import com.kesi.planit.alarm.application.repository.AlarmTypeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlarmTypeService {
    private final List<AlarmTypeRepo> alarmTypeRepo;


}
