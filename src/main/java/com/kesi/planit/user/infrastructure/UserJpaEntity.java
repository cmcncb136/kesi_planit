package com.kesi.planit.user.infrastructure;

import com.kesi.planit.calendar.domain.Calendar;
import com.kesi.planit.user.domain.Role;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "user_table")
@NoArgsConstructor
public class UserJpaEntity {
    @Id
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

    private Long calendarId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public UserJpaEntity(String uid, String email, String nickname, String imagePath, String gender, LocalDate birth, LocalDate joinDate, Long calendarId, Role role) {
        this.uid = uid;
        this.email = email;
        this.nickname = nickname;
        this.imagePath = imagePath;
        this.gender = gender;
        this.birth = birth;
        this.joinDate = joinDate;
        this.calendarId = calendarId;
    }

    public static UserJpaEntity from(User user) {
        return UserJpaEntity.builder()
                .uid(user.getUid())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .imagePath(user.getImgPath())
                .gender(user.getGender())
                .birth(user.getBirth())
                .joinDate(user.getJoinDate())
                .calendarId(user.getMyCalendar().getId())
                .role(user.getRole())
                .build();
    }

    public User toModel(Calendar calendar){
        return User.builder()
                .uid(this.uid)
                .email(this.email)
                .nickname(this.nickname)
                .birth(birth)
                .joinDate(this.joinDate)
                .gender(this.gender)
                .imgPath(this.imagePath)
                .myCalendar(calendar)
                .role(this.role)
                .build();
    }
}
