package com.kesi.planit.group.Presentation;


import com.google.api.services.storage.Storage;
import com.kesi.planit.group.Presentation.dto.GroupDto;
import com.kesi.planit.group.Presentation.dto.GroupMakeInfoRequestDto;
import com.kesi.planit.group.Presentation.dto.GroupSimpleDto;
import com.kesi.planit.group.application.GroupService;
import com.kesi.planit.group.domain.Group;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("group")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("")
    public ResponseEntity<Long> createGroup(@RequestBody GroupMakeInfoRequestDto groupMakeInfoRequestDto,
                                              HttpServletRequest request) {
        return groupService.createGroup((String)request.getAttribute("uid") ,groupMakeInfoRequestDto);
    }

    @GetMapping("")
    public ResponseEntity<GroupDto> getGroup(HttpServletRequest request, @RequestParam("gid") Long gid) {
        return groupService.getGroupDto(gid, (String)request.getAttribute("uid"));
    }

    @GetMapping("/list")
    public ResponseEntity<List<GroupSimpleDto>> getAllGroups(HttpServletRequest request) {
        return groupService.getGroupSimpleDto((String)request.getAttribute("uid"));
    }
}
