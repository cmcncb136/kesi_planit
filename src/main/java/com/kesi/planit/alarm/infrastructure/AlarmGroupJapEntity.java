package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.domain.AlarmGroup;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="alarm_group")
@NoArgsConstructor
public class AlarmGroupJapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long alarmId;

    private Long gid;

    @Builder
    public AlarmGroupJapEntity(Long id, Long alarmId, Long gid) {
        this.id = id;
        this.alarmId = alarmId;
        this.gid = gid;
    }

    public AlarmGroup toModel(Alarm alarm){
        return AlarmGroup.builder()
                .id(this.id)
                .gid(this.gid)
                .alarm(alarm)
                .build();
    }

    public static AlarmGroupJapEntity from(AlarmGroup alarmGroup){
        return  AlarmGroupJapEntity.builder()
                .id(alarmGroup.getId())
                .alarmId(alarmGroup.getAlarm().getId())
                .gid(alarmGroup.getGid())
                .build();
    }
}
