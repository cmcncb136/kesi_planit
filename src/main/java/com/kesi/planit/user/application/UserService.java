package com.kesi.planit.user.application;

import com.kesi.planit.user.application.repository.UserRepo;
import com.kesi.planit.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    @Transactional
    public User getUserById(String uid) {
        User user = userRepo.findById(uid).toModel();
        return user;
    }
}
