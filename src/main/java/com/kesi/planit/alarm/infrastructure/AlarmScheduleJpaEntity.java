package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.domain.AlarmSchedule;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alarm_schedule")
@NoArgsConstructor
public class AlarmScheduleJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long alarmId;

    private Long gid;

    private Long scheduleId;

    @Builder
    public AlarmScheduleJpaEntity(Long id, Long alarmId, Long gid, Long scheduleId) {
        this.id = id;
        this.alarmId = alarmId;
        this.gid = gid;
        this.scheduleId = scheduleId;
    }

    public AlarmSchedule toModel(Alarm alarm){
        return AlarmSchedule.builder()
                .id(this.id)
                .alarm(alarm)
                .gid(this.gid)
                .scheduleId(this.scheduleId)
                .build();
    }

    public static AlarmScheduleJpaEntity from(AlarmSchedule alarmSchedule){
        return AlarmScheduleJpaEntity.builder()
                .id(alarmSchedule.getId())
                .alarmId(alarmSchedule.getAlarm().getId())
                .gid(alarmSchedule.getGid())
                .scheduleId(alarmSchedule.getScheduleId())
                .build();
    }
}
