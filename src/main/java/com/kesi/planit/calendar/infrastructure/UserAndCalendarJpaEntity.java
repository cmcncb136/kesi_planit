package com.kesi.planit.calendar.infrastructure;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.calendar.domain.UserAndCalendar;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserAndCalendarJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long calendarId;
    private String uid;

    @Builder
    public UserAndCalendarJpaEntity(Long id, Long calendarId, String uid) {
        this.id = id;
        this.calendarId = calendarId;
        this.uid = uid;
    }

    public UserAndCalendar toModel(User user, Calendar calendar){
        return UserAndCalendar.builder()
                .user(user)
                .calendar(calendar)
                .id(id)
                .build();
    }

    public static UserAndCalendarJpaEntity from(UserAndCalendar userAndCalendar){
        return UserAndCalendarJpaEntity.builder()
                .id(userAndCalendar.getId())
                .uid(userAndCalendar.getUser().getUid())
                .calendarId(userAndCalendar.getCalendar().getId())
                .build();
    }

}
