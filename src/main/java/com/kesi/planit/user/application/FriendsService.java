package com.kesi.planit.user.application;

import com.kesi.planit.core.CommonResult;
import com.kesi.planit.user.application.repository.FriendsRelationRepo;
import com.kesi.planit.user.domain.FriendsRelation;
import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.infrastructure.FriendsRelationJpaEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsService {
    private FriendsRelationRepo friendsRelationRepo;
    private UserService userService;

    //친구 관계 정보를 반환
    public List<FriendsRelation> getFriendsRelationsByUid(String uid) {
        User sourceUser = userService.getUserById(uid);
        return friendsRelationRepo.findBySourceEmail(sourceUser.getEmail()).stream().
                map(it -> it.toModel(
                        sourceUser,
                        userService.getUserByEmail(it.getTargetEmail()
        ))).toList();
    }

    //친구 정보를 반환
    public List<User> getFriendsByUid(String uid){
        User sourceUser = userService.getUserById(uid);
        return friendsRelationRepo.findBySourceEmail(sourceUser.getEmail()).stream().map(
                it -> userService.getUserByEmail(it.getTargetEmail())
        ).toList();
    }

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
