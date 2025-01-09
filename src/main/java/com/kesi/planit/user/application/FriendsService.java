package com.kesi.planit.user.application;

import com.kesi.planit.core.CommonResult;
import com.kesi.planit.user.application.repository.FriendsRelationRepo;
import com.kesi.planit.user.domain.FriendsRelation;
import com.kesi.planit.user.domain.User;
import com.kesi.planit.user.infrastructure.FriendsRelationJpaEntity;
import com.kesi.planit.user.presentation.dto.FriendUpdateRequestDto;
import com.kesi.planit.user.presentation.dto.FriendsDto;
import org.springframework.http.ResponseEntity;
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
                        userService.getUserByEmail(it.getTargetEmail()))).toList();
    }

    //친구 별칭 업데이트
    public ResponseEntity<String> updateFriendsRelation(FriendUpdateRequestDto requestDto, String sourceUid) {
        User targetUser = userService.getUserByEmail(requestDto.targetEmail);
        User sourceUser = userService.getUserById(sourceUid);
        if(targetUser == null) {
            return ResponseEntity.badRequest().body("Target email doesn't exist");
        }

        FriendsRelationJpaEntity relationJpaEntity =
                friendsRelationRepo.findBySourceEmailAndTargetEmail(sourceUser.getEmail(), targetUser.getEmail());

        if(relationJpaEntity == null) {
            return ResponseEntity.badRequest().body("relation doesn't exist");
        }

        FriendsRelation relation = relationJpaEntity.toModel(sourceUser, targetUser);
        relation.setAlias(requestDto.alias);
        save(relation);


        return ResponseEntity.ok().body("ok");
    }

    //친구 정보를 반환
    public List<FriendsDto> getFriendsByUid(String uid){
        User sourceUser = userService.getUserById(uid);
        return friendsRelationRepo.findBySourceEmail(sourceUser.getEmail()).stream().map(
                it -> FriendsDto.from(
                        userService.getUserByEmail(it.getTargetEmail()), it.getAlias()
                )
        ).toList();
    }

    //친구 검색
    public ResponseEntity<String> addFriends(String uid, String targetEmail) {
        User source = userService.getUserById(uid);
        User target;
        try{
            target = userService.getUserById(targetEmail);
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }

        friendsRelationRepo.save(FriendsRelationJpaEntity.builder()
                        .sourceEmail(source.getEmail())
                        .targetEmail(target.getEmail())
                .build());

        return ResponseEntity.ok("Added friends");
    }

    private FriendsRelation save(FriendsRelation friendsRelation) {
        return friendsRelationRepo.save(FriendsRelationJpaEntity.from(friendsRelation))
                .toModel(friendsRelation.getSource(), friendsRelation.getTarget());
    }
}
