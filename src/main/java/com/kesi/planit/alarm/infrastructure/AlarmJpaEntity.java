package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.domain.Alarm;
import com.kesi.planit.alarm.domain.AlarmData;
import com.kesi.planit.alarm.domain.AlarmType;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Entity
@Table(name = "alarm")
@NoArgsConstructor
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

    @Builder
    public AlarmJpaEntity(Long id, String uid, String title, String content, AlarmType alarmType) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.alarmType = alarmType;
    }

    public Alarm toModel(User user, AlarmData alarmData) {
        return Alarm.builder()
                .id(id)
                .user(user)
                .title(title)
                .content(content)
                .alarmType(alarmType)
                .createTime(createTime)
                .data(alarmData)
                .build();
    }

    public static AlarmJpaEntity from(Alarm alarm) {
        return AlarmJpaEntity.builder()
                .id(alarm.getId())
                .uid(alarm.getUser().getUid())
                .title(alarm.getTitle())
                .content(alarm.getContent())
                .alarmType(alarm.getAlarmType())
                .build();
    }
}
