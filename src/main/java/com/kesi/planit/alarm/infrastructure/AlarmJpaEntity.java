package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.domain.AlarmStatus;
import com.kesi.planit.alarm.domain.AlarmType;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name = "alarm")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AlarmJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @CreatedDate //생성일시가 자동으로 저장됨
    private LocalDateTime createTime;

    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmState;

    @Builder
    public AlarmJpaEntity(Long id, String uid, String title, String content, AlarmType alarmType, AlarmStatus alarmState) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.alarmType = alarmType;
        this.alarmState = alarmState;
    }

    public Alarm toModel(User user) {
        return Alarm.builder()
                .id(id)
                .user(user)
                .title(title)
                .content(content)
                .alarmType(alarmType)
                .createTime(createTime)
                .alarmState(alarmState)
                .build();
    }

    public static AlarmJpaEntity from(Alarm alarm) {
        return AlarmJpaEntity.builder()
                .id(alarm.getId())
                .uid(alarm.getUser().getUid())
                .title(alarm.getTitle())
                .content(alarm.getContent())
                .alarmType(alarm.getAlarmType())
                .alarmState(alarm.getAlarmState())
                .build();
    }
}
