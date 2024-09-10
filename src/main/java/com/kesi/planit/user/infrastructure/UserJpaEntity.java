package com.kesi.planit.user.infrastructure;

import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
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

    private LocalDate birth;

    private LocalDate joinDate;

    @Builder
    public UserJpaEntity(String uid, String email, String nickname, String imagePath, String gender, LocalDate birth, LocalDate joinDate) {
        this.uid = uid;
        this.email = email;
        this.nickname = nickname;
        this.imagePath = imagePath;
        this.gender = gender;
        this.birth = birth;
        this.joinDate = joinDate;
    }

    public static UserJpaEntity from(User user) {
        UserJpaEntity result = new UserJpaEntity();
        result.uid = user.getUid();
        result.email = user.getEmail();
        result.nickname = user.getNickname();
        result.birth = user.getBirth();
        result.joinDate = user.getJoinDate();
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
                .joinDate(this.joinDate)
                .gender(this.gender)
                .imgPath(this.imagePath)
                .build();
    }
}
