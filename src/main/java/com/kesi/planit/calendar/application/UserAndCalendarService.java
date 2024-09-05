package com.kesi.planit.calendar.application;

import com.kesi.planit.calendar.application.repository.UserAndCalendarRepo;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.calendar.domain.UserAndCalendar;
import com.kesi.planit.calendar.infrastructure.UserAndCalendarJpaEntity;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserAndCalendarService {
    private UserAndCalendarRepo userAndCalendarRepo;
    private UserService userService;
    private CalendarService calendarService;

    public UserAndCalendar getById(long id) {
        UserAndCalendarJpaEntity userAndCalendarJpaEntity
                = userAndCalendarRepo.findById(id);
        User user = userService.getUserById(userAndCalendarJpaEntity.getUid());
        Calendar calendar = calendarService.getById(userAndCalendarJpaEntity.getCalendarId());

        return  userAndCalendarJpaEntity.toModel(user, calendar);
    }

    public UserAndCalendar save(UserAndCalendar userAndCalendar) {
        return userAndCalendarRepo.save(UserAndCalendarJpaEntity.from(userAndCalendar)).toModel(
                userAndCalendar.getUser(), userAndCalendar.getCalendar()
        );
    }

    //Calendar Id가 같은 값을 가져온다.
    //User 객체를 만들어 도메인으로 매핑시킨다.
    public List<UserAndCalendar> getByCalendar(Calendar calendar) {
        List<UserAndCalendarJpaEntity> userAndCalendarJpaEntityList
                = userAndCalendarRepo.findByCalendarId(calendar.getId());

        List<UserAndCalendar> userAndCalendarList = new ArrayList<>();

        for(UserAndCalendarJpaEntity userAndCalendarJpaEntity : userAndCalendarJpaEntityList) {
            User user = userService.getUserById(userAndCalendarJpaEntity.getUid());
            userAndCalendarList.add(userAndCalendarJpaEntity.toModel(user, calendar));
        }

        return userAndCalendarList;
    }

    public List<UserAndCalendar> getByUser(User user) {
        List<UserAndCalendarJpaEntity> userAndCalendarJpaEntityList
                = userAndCalendarRepo.findByUid(user.getUid());

        List<UserAndCalendar> userAndCalendarList = new ArrayList<>();

        for(UserAndCalendarJpaEntity userAndCalendarJpaEntity : userAndCalendarJpaEntityList) {
            Calendar calendar = calendarService.getById(userAndCalendarJpaEntity.getCalendarId());
            userAndCalendarList.add(userAndCalendarJpaEntity.toModel(user, calendar));
        }

        return userAndCalendarList;
    }
}
