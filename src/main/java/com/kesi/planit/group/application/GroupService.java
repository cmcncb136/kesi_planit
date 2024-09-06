package com.kesi.planit.group.application;

import com.kesi.planit.group.application.repository.GroupRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepo groupRepo;
}
