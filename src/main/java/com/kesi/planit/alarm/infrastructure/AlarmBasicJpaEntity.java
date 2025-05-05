package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.domain.AlarmBasic;
import com.kesi.planit.alarm.domain.AlarmGroup;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="alarm_basic")
@NoArgsConstructor
@Getter
public class AlarmBasicJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long alarmId;

    @Builder
    public AlarmBasicJpaEntity(Long id, Long alarmId) {
        this.id = id;
        this.alarmId = alarmId;
    }

    public AlarmBasic toModel(Alarm alarm){
        return AlarmBasic.builder()
                .id(this.id)
                .alarm(alarm)
                .build();
    }

    public static AlarmBasicJpaEntity from(AlarmBasic alarmBasic){
        return  AlarmBasicJpaEntity.builder()
                .id(alarmBasic.getId())
                .alarmId(alarmBasic.getAlarm().getId())
                .build();
    }
}
