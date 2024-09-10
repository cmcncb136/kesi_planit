package com.kesi.planit.user.application;

import com.kesi.planit.core.CommonResult;
import com.kesi.planit.user.application.repository.FriendsRelationRepo;
import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.infrastructure.FriendsRelationJpaEntity;

public class FriendsService {
    private FriendsRelationRepo friendsRelationRepo;
    private UserService userService;

    //Todo. email 검색시 친구 목록을 반환하는 코드 작성

    //친구 검색
    public CommonResult addFriends(String uid, String targetEmail) {
        User source = userService.getUserById(uid);
        User target;
        try{
            target = userService.getUserById(targetEmail);
        }catch (NullPointerException e){
            return new CommonResult(400, "Don't find friends", false);
        }

        friendsRelationRepo.save(FriendsRelationJpaEntity.builder()
                        .sourceEmail(source.getEmail())
                        .targetEmail(target.getEmail())
                .build());

        return new CommonResult(200, "Added friends", true);
    }
}
