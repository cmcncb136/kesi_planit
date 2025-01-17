package com.kesi.planit.schedule.infrastructure;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.schedule.domain.Schedule;
import com.kesi.planit.schedule.domain.ScheduleSecurity;
import com.kesi.planit.schedule.domain.SecurityLevel;
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

    @Enumerated(EnumType.STRING)
    private SecurityLevel securityLevel;

    @Builder
    public ScheduleSecurityEntity(Long id, Long scheduleId, String uid, SecurityLevel securityLevel) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.uid = uid;
        this.securityLevel = securityLevel;
    }

    public ScheduleSecurity toModel(Schedule schedule, User user) {
        return ScheduleSecurity.builder()
                .id(this.id)
                .schedule(schedule)
                .user(user)
                .securityLevel(this.securityLevel)
                .build();
    }

    public static ScheduleSecurityEntity from(ScheduleSecurity scheduleSecurity) {
        return ScheduleSecurityEntity.builder()
                .id(scheduleSecurity.getId())
                .scheduleId(scheduleSecurity.getSchedule().getId())
                .uid(scheduleSecurity.getUser().getUid())
                .securityLevel(scheduleSecurity.getSecurityLevel())
                .build();
    }
}
