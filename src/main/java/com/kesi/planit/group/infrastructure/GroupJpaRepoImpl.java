package com.kesi.planit.group.infrastructure;

import com.kesi.planit.group.application.repository.GroupRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GroupJpaRepoImpl implements GroupRepo {
    private final GroupJpaRepository groupJpaRepo;

    @Override
    public GroupJpaEntity save(GroupJpaEntity groupJpaEntity) {
        return groupJpaRepo.save(groupJpaEntity);
    }

    @Override
    public GroupJpaEntity findById(Long id) {
        return groupJpaRepo.findById(id).orElse(null);
    }

    @Override
    public GroupJpaEntity findByCalendarId(Long calendarId) {
        return groupJpaRepo.findByCalendarId(calendarId);
    }

    @Override
    public void deleteById(Long id) {
        groupJpaRepo.deleteById(id);
    }
}
