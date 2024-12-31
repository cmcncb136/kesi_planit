package com.kesi.planit.user;

import com.kesi.planit.user.application.repository.UserRepo;
import com.kesi.planit.user.infrastructure.UserJpaEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;

@SpringBootTest //DataJpaTest는 Repository 관련된 빈만 만들어서 내가 원하는 테스트 하기 힘듦
@Transactional
//@DataJpaTest //Jpa Repository에 대한 검증을 수행할 때 사용하는 어노테이션
//Transaction를 포함하고 있어 1개의 테스트가 끝나면 Rollback해 다른 테스트에 영향을 주지 않는다!
//@AutoConfigurationDatabase에 Replace.NONE설정을 주면 실제 DB로 검증할 수 있다.
//따로 명시하지 않을 시 내장된 임베디드 DB를 사용
public class UserRepositoryTest {
    @Autowired
    UserRepo userRepository;

    @Test
    @DisplayName("유저 생성")
    void creatUser(){
        //given
        UserJpaEntity userJpaEntity = UserJpaEntity.builder().uid("x").email("x@naver.com")
                .birth(LocalDate.of(2001, 11, 11)).joinDate(LocalDate.now())
                .gender("Male").calendarId(1L).nickname("kim").imagePath("x.png").build();

        //when
        UserJpaEntity result = userRepository.save(userJpaEntity);

        //then
        assertThat(result.getUid()).isEqualTo(userJpaEntity.getUid());
        assertThat(result.getEmail()).isEqualTo(userJpaEntity.getEmail());
        assertThat(result.getBirth()).isEqualTo(userJpaEntity.getBirth());
        assertThat(result.getGender()).isEqualTo(userJpaEntity.getGender());
        assertThat(result.getCalendarId()).isEqualTo(userJpaEntity.getCalendarId());
        assertThat(result.getNickname()).isEqualTo(userJpaEntity.getNickname());
        assertThat(result.getImagePath()).isEqualTo(userJpaEntity.getImagePath());
    }

    @Test
    @DisplayName("유저 아이디 조회")
    void findByUid(){
        //given
        UserJpaEntity userJpaEntity = UserJpaEntity.builder().uid("x").email("x@naver.com")
                .birth(LocalDate.of(2001, 11, 11)).joinDate(LocalDate.now())
                .gender("Male").calendarId(1L).nickname("kim").imagePath("x.png").build();

        userRepository.save(userJpaEntity);

        //when
        UserJpaEntity result = userRepository.findById(userJpaEntity.getUid());

        //then
        assertThat(result.getUid()).isEqualTo(userJpaEntity.getUid());
        assertThat(result.getEmail()).isEqualTo(userJpaEntity.getEmail());
        assertThat(result.getBirth()).isEqualTo(userJpaEntity.getBirth());
        assertThat(result.getGender()).isEqualTo(userJpaEntity.getGender());
        assertThat(result.getCalendarId()).isEqualTo(userJpaEntity.getCalendarId());
        assertThat(result.getNickname()).isEqualTo(userJpaEntity.getNickname());
        assertThat(result.getImagePath()).isEqualTo(userJpaEntity.getImagePath());
    }


    @Test
    @DisplayName("유저 이메일 조회")
    void findByEmail(){
        //given
        UserJpaEntity userJpaEntity = UserJpaEntity.builder().uid("x").email("x@naver.com")
                .birth(LocalDate.of(2001, 11, 11)).joinDate(LocalDate.now())
                .gender("Male").calendarId(1L).nickname("kim").imagePath("x.png").build();

        UserJpaEntity tmp = userRepository.findById(userJpaEntity.getUid());
        if(tmp != null) System.out.println("No Result tmp " + tmp.toString());
        else System.out.println("Result");

        userRepository.save(userJpaEntity);

        //when
        UserJpaEntity result = userRepository.findByEmail(userJpaEntity.getEmail());

        //then
        assertThat(result.getUid()).isEqualTo(userJpaEntity.getUid());
        assertThat(result.getEmail()).isEqualTo(userJpaEntity.getEmail());
        assertThat(result.getBirth()).isEqualTo(userJpaEntity.getBirth());
        assertThat(result.getGender()).isEqualTo(userJpaEntity.getGender());
        assertThat(result.getCalendarId()).isEqualTo(userJpaEntity.getCalendarId());
        assertThat(result.getNickname()).isEqualTo(userJpaEntity.getNickname());
        assertThat(result.getImagePath()).isEqualTo(userJpaEntity.getImagePath());
    }

}
