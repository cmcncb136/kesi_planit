package com.kesi.planit.user.application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.kesi.planit.calendar.application.repository.CalendarRepo;
import com.kesi.planit.calendar.application.repository.UserAndCalendarRepo;
import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.calendar.domain.UserAndCalendar;
import com.kesi.planit.calendar.infrastructure.UserAndCalendarJpaEntity;
import com.kesi.planit.core.CommonResult;
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
    private final UserAndCalendarRepo userAndCalendarRepo;

    @Transactional
    public User getUserById(String uid) {
        User user = userRepo.findById(uid).toModel();
        return user;
    }

    //친구 추가시 NPE 발생할 수 있음
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).toModel();
    }


    //Calendar Id가 같은 값을 가져온다.
    //User 객체를 만들어 도메인으로 매핑시킨다.
    public List<User> getByCalendar(Calendar calendar) {
        List<UserAndCalendarJpaEntity> userAndCalendarJpaEntityList
                = userAndCalendarRepo.findByCalendarId(calendar.getId());

        return userAndCalendarJpaEntityList.stream().
                map(it -> getUserById(it.getUid())).toList();
    }


    public CommonResult join(String uid, UserJoinRequestDto joinUser) throws FirebaseAuthException {
        String email = FirebaseAuth.getInstance().getUser(uid).getEmail();
        User user = joinUser.toModel(uid, email, "basic.jpg", LocalDate.now());
        user = save(user);

        if(user == null)
            return new CommonResult(400, "join fail!", false);

        return new CommonResult(200, "join success!", true);
    }


    public User save(User user){
        return userRepo.save(UserJpaEntity.from(user)).toModel();
    }
}
