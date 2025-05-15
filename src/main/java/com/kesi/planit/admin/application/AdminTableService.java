package com.kesi.planit.admin.application;

import com.kesi.planit.admin.application.repository.TableRepo;
import com.kesi.planit.core.RoleCheck;
import com.kesi.planit.user.domain.Role;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AdminTableService {
    private final TableRepo tableRepo;

    @RoleCheck(allowLevel = Role.ADMIN)
    public Page<Map<String, Object>> findAllTable(User user, TableName tableName, Pageable pageable) {
        return tableRepo.findAll(tableName, pageable);
    }
}
