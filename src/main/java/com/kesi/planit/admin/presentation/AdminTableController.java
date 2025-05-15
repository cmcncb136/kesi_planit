package com.kesi.planit.admin.presentation;

import com.kesi.planit.admin.application.AdminFacade;
import com.kesi.planit.admin.application.TableName;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor 
@RequestMapping("admin/table")
public class AdminTableController {
    private final AdminFacade adminFacade;

    @GetMapping("")
    public Page<Map<String, Object>> getTable(HttpServletRequest request, TableName tableName, int page, int size) {
        return adminFacade.getTable((String)request.getAttribute("uid"), tableName, page, size);
    }
}
