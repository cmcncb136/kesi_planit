package com.kesi.planit.group.application.repository;

import com.kesi.planit.group.infrastructure.GroupJpaEntity;
import org.springframework.stereotype.Component;

public interface GroupRepo {
    GroupJpaEntity save(GroupJpaEntity groupJpaEntity);
    GroupJpaEntity findById(Long id);
    GroupJpaEntity findByCalendarId(Long calendarId);
    void deleteById(Long id);
}
