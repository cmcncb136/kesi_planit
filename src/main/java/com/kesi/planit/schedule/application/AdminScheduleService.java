package com.kesi.planit.schedule.application;

import com.kesi.planit.core.RoleCheck;
import com.kesi.planit.schedule.presentation.dto.ScheduleSourceDto;
import com.kesi.planit.user.domain.Role;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminScheduleService {
    private final ScheduleSourceService scheduleSourceService;

    @RoleCheck(allowLevel = Role.ADMIN)
    public Page<ScheduleSourceDto> getScheduleSources(User user, Pageable pageable) {
        return scheduleSourceService.getScheduleSources(pageable).map(ScheduleSourceDto::from);
    }
}
