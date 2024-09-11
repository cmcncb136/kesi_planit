package com.kesi.planit.user.infrastructure;

import com.kesi.planit.user.domain.FriendsRelation;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "friends_relation")
public class FriendsRelationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //친구 관계를 만든 사람.
    private String sourceEmail;

    //친구 관계를 당한 사람
    private String targetEmail;

    @Builder
    public FriendsRelationJpaEntity(String sourceEmail, String targetEmail) {
        this.sourceEmail = sourceEmail;
        this.targetEmail = targetEmail;
    }

    public FriendsRelation toModel(User source, User target){
        return FriendsRelation.builder()
                .source(source)
                .target(target)
                .build();
    }

    public static FriendsRelationJpaEntity from(FriendsRelation relation){
        return FriendsRelationJpaEntity.builder()
                .sourceEmail(relation.getSource().getEmail())
                .targetEmail(relation.getTarget().getEmail())
                .build();
    }
}
