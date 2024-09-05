package com.kesi.planit.calendar.infrastructure;

import com.kesi.planit.calendar.application.repository.UserAndCalendarRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserAndCalendarRepoImpl implements UserAndCalendarRepo {
    private UserAndCalendarJapRepo userAndCalendarJapRepo;

    @Override
    public UserAndCalendarJpaEntity findById(Long id) {
        return userAndCalendarJapRepo.findById(id).orElse(null);
    }

    @Override
    public List<UserAndCalendarJpaEntity> findByUid(String uid) {
        return userAndCalendarJapRepo.findByUid(uid);
    }

    @Override
    public List<UserAndCalendarJpaEntity> findByCalendarId(Long calendarId) {
        return userAndCalendarJapRepo.findByCalendarId(calendarId);
    }

    @Override
    public UserAndCalendarJpaEntity save(UserAndCalendarJpaEntity userAndCalendarJpaEntity) {
        return save(userAndCalendarJpaEntity);
    }
}
