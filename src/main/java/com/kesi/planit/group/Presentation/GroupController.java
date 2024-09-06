package com.kesi.planit.group.Presentation;


import com.google.api.services.storage.Storage;
import com.kesi.planit.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    @PostMapping("/create")
    public void createGroup(@RequestBody Group group) {
        //group
    }
}
