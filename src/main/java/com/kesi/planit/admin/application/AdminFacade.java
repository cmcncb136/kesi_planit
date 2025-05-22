package com.kesi.planit.admin.application;

import com.kesi.planit.core.PageRequestFactory;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class AdminFacade {
    private final AdminTableService adminTableService;
    private final UserService userService;

    public Page<Map<String, Object>> getTable(String uid, TableName tableName, int page, int size) {
        User user = userService.getById(uid);
        Pageable pageable = PageRequestFactory.of(page, size);

        return adminTableService.findAllTable(user, tableName, pageable);
    }

}













