package com.kesi.planit.admin.application.repository;

import com.kesi.planit.admin.application.TableName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface TableRepo {
    Page<Map<String, Object>> findAll(TableName tableName, Pageable pageable);
}
