package com.kesi.planit.schedule.infrastructure;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "schedule_security")
public class ScheduleSecurityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long scheduleId;
    private String uid;
    private Integer securityLevel;

    @Builder
    public ScheduleSecurityEntity(Long id, Long scheduleId, String uid, Integer securityLevel) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.uid = uid;
        this.securityLevel = securityLevel;
    }

}
