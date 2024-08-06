package com.kesi.planit.user.infrastructure;

import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String nickname;

    public static UserJpaEntity from(User user) {
        UserJpaEntity result = new UserJpaEntity();
        result.id = user.getId();
        result.email = user.getEmail();
        result.nickname = user.getNickname();
        return result;
    }

    public User toModel(){
        return User.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .build();
    }
}
