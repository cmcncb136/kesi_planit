package com.kesi.planit.user.presentation;

import com.kesi.planit.core.CommonResult;
import com.kesi.planit.user.application.FriendsService;
import com.kesi.planit.user.presentation.dto.FriendsDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/friends")
public class FriendsController {
    private final FriendsService friendsService;

    @GetMapping("/")
    public List<FriendsDto> getFriends(HttpServletRequest request) {
        return friendsService.getFriendsByUid(request.getAttribute("uid").toString())
                .stream().map(it -> FriendsDto.from(it)).toList();
    }

    @PostMapping("/")
    public CommonResult addFriends(@Param("email") String email, HttpServletRequest request){
        return friendsService.addFriends(
                request.getAttribute("uid").toString(),
                email
        );
    }
}
