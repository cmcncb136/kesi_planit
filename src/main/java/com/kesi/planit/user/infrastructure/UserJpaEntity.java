package com.kesi.planit.user.infrastructure;

import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 150) //현재 naver server에 mariaDB에 key 키 제한이 255(default) 보다 작아서 지정해 줌
    private String uid;

    @Column(unique = true)
    private String email;

    @Column
    private String nickname;

    private String imagePath;

    private String gender;

    private String birth;


    public static UserJpaEntity from(User user) {
        UserJpaEntity result = new UserJpaEntity();
        result.uid = user.getUid();
        result.email = user.getEmail();
        result.nickname = user.getNickname();
        result.birth = user.getBirth();
        result.gender = user.getGender();
        result.imagePath = user.getImgPath();
        return result;
    }

    public User toModel(){
        return User.builder()
                .uid(this.uid)
                .email(this.email)
                .nickname(this.nickname)
                .birth(birth)
                .gender(this.gender)
                .imgPath(this.imagePath)
                .build();
    }
}
