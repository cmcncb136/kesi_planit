package com.kesi.planit.user.application;

import com.kesi.planit.calendar.application.repository.CalendarRepo;
import com.kesi.planit.calendar.application.repository.UserAndCalendarRepo;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.calendar.domain.UserAndCalendar;
import com.kesi.planit.calendar.infrastructure.UserAndCalendarJpaEntity;
import com.kesi.planit.user.application.repository.UserRepo;
import com.kesi.planit.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserAndCalendarRepo userAndCalendarRepo;

    @Transactional
    public User getUserById(String uid) {
        User user = userRepo.findById(uid).toModel();
        return user;
    }


    //Calendar Id가 같은 값을 가져온다.
    //User 객체를 만들어 도메인으로 매핑시킨다.
    public List<User> getByCalendar(Calendar calendar) {
        List<UserAndCalendarJpaEntity> userAndCalendarJpaEntityList
                = userAndCalendarRepo.findByCalendarId(calendar.getId());

        return userAndCalendarJpaEntityList.stream().
                map(it -> getUserById(it.getUid())).toList();
    }

}
