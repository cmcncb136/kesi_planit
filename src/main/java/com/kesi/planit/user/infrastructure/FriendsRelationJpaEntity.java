package com.kesi.planit.user.infrastructure;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
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
}
