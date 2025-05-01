package com.kesi.planit.user.application;

import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.group.application.repository.GroupAndUserRepo;
import com.kesi.planit.group.infrastructure.GroupAndUserJpaEntity;
import com.kesi.planit.user.application.repository.UserRepo;
import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.infrastructure.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final GroupAndUserRepo groupAndUserRepo;
    private final CalendarService calendarService;

    @Transactional
    public User getUserById(String uid) {
        UserJpaEntity userJpaEntity = userRepo.findById(uid);
        return userJpaEntity.toModel(
                calendarService.getById(userJpaEntity.getCalendarId()));
    }

    //친구 추가시 존재하지 않는 이메일에 대해서 NPE 발생할 수 있음
    public User getUserByEmail(String email) throws NullPointerException {
        UserJpaEntity userJpaEntity = userRepo.findByEmail(email);
        return userJpaEntity.toModel(
                calendarService.getById(userJpaEntity.getCalendarId())
        );
    }

    //User 객체를 만들어 도메인으로 매핑시킨다.
    public List<User> getByGid(Long gid) {
        List<GroupAndUserJpaEntity> userAndCalendarJpaEntityList = groupAndUserRepo.findByGid(gid);

        return userAndCalendarJpaEntityList.stream().
                map(it -> getUserById(it.getUid())).toList();
    }

    public Page<User> getUsers(Pageable pageable) {
        Page<UserJpaEntity> userJpaEntityList = userRepo.findAll(pageable);
        return userJpaEntityList.map(it -> it.toModel(calendarService.getById(it.getCalendarId())));
    }



    public User save(User user){
        return userRepo.save(UserJpaEntity.from(user)).toModel(user.getMyCalendar());
    }
}
