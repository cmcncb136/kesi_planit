package com.kesi.planit.admin.infrastructure;

import com.kesi.planit.admin.application.TableName;
import com.kesi.planit.admin.application.repository.TableRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class TableRepoImpl implements TableRepo {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Page<Map<String, Object>> findAll(TableName tableName, Pageable pageable) {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName.name(), Integer.class);

        String sql = "SELECT * FROM " + tableName.name() + " LIMIT " + pageable.getOffset() + "," + pageable.getPageSize();
        List<Map<String, Object>> rst = jdbcTemplate.queryForList(sql);
        return new PageImpl<>(rst, pageable, count);
    }
}
