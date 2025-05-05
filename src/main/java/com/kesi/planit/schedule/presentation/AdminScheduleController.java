package com.kesi.planit.schedule.presentation;

import com.kesi.planit.schedule.application.ScheduleFacade;
import com.kesi.planit.schedule.application.ScheduleService;
import com.kesi.planit.schedule.presentation.dto.ScheduleSourceDto;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("schedule")
public class AdminScheduleController {
    private final ScheduleFacade scheduleFacade;

    @GetMapping("/sources")
    public ResponseEntity<Page<ScheduleSourceDto>> getSources(HttpServletRequest request, int page, int size, ServletResponse servletResponse) {
        return scheduleFacade.getScheduleSources((String)request.getAttribute("uid"), page, size);
    }
}
