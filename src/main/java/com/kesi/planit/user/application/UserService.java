package com.kesi.planit.user.application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.kesi.planit.calendar.application.CalendarService;
import com.kesi.planit.calendar.application.UserAndCalendarService;
import com.kesi.planit.calendar.application.repository.CalendarRepo;
import com.kesi.planit.calendar.application.repository.UserAndCalendarRepo;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.calendar.domain.UserAndCalendar;
import com.kesi.planit.calendar.infrastructure.UserAndCalendarJpaEntity;
import com.kesi.planit.core.CommonResult;
import com.kesi.planit.group.application.repository.GroupAndUserRepo;
import com.kesi.planit.group.domain.Group;
import com.kesi.planit.group.infrastructure.GroupAndUserJpaEntity;
import com.kesi.planit.user.application.repository.UserRepo;
import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.infrastructure.UserJpaEntity;
import com.kesi.planit.user.presentation.dto.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final GroupAndUserRepo groupAndUserRepo;

    @Transactional
    public User getUserById(String uid) {
        return userRepo.findById(uid).toModel();
    }

    //친구 추가시 존재하지 않는 이메일에 대해서 NPE 발생할 수 있음
    public User getUserByEmail(String email) throws NullPointerException {
        return userRepo.findByEmail(email).toModel();
    }

    //User 객체를 만들어 도메인으로 매핑시킨다.
    public List<User> getByGid(Long gid) {
        List<GroupAndUserJpaEntity> userAndCalendarJpaEntityList
                = groupAndUserRepo.findByGid(gid);

        return userAndCalendarJpaEntityList.stream().
                map(it -> getUserById(it.getUid())).toList();
    }



    public User save(User user){
        return userRepo.save(UserJpaEntity.from(user)).toModel();
    }
}
