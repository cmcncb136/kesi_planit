package com.kesi.planit.user.presentation;

import com.kesi.planit.core.CommonResult;
import com.kesi.planit.user.application.FriendsService;
import com.kesi.planit.user.presentation.dto.FriendUpdateRequestDto;
import com.kesi.planit.user.presentation.dto.FriendsDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("friends")
public class FriendsController {
    private final FriendsService friendsService;

    @GetMapping("")
    public ResponseEntity<List<FriendsDto>> getFriends(HttpServletRequest request) {
        return ResponseEntity.ok(friendsService.getFriendsByUid((String) request.getAttribute("uid")));
    }

    @PutMapping("")
    public ResponseEntity<String> updateFriends(@RequestBody FriendUpdateRequestDto friendsDto, HttpServletRequest request) {
        return friendsService.updateFriendsRelation(friendsDto, (String) request.getAttribute("uid"));
    }

    @PostMapping("")
    public ResponseEntity<String> addFriends(@Param("email") String email, HttpServletRequest request){
        return friendsService.addFriends(
                request.getAttribute("uid").toString(),
                email
        );
    }
}
