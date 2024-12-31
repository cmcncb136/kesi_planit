package com.kesi.planit.group;

import com.kesi.planit.group.application.repository.GroupRepo;
import com.kesi.planit.group.infrastructure.GroupJpaEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class GroupRepositoryTest {
    @Autowired
    GroupRepo groupRepository;

    @Test
    @DisplayName("그룹 생성")
    void createGroup(){
        //given
        GroupJpaEntity groupJpaEntity = GroupJpaEntity.builder()
                .groupName("First Group")
                .calendarId(1L)
                .makerUid("x")
                .build();

        //when
        GroupJpaEntity result = groupRepository.save(groupJpaEntity);

        //then
        assertThat(result.getGroupName()).isEqualTo("First Group");
        assertThat(result.getCalendarId()).isEqualTo(1L);
        assertThat(result.getMakerUid()).isEqualTo("x");
    }

    @Test
    @DisplayName("그룹 gid 조회")
    void findByGid(){
        //given
        GroupJpaEntity groupJpaEntity = GroupJpaEntity.builder()
                .groupName("First Group")
                .calendarId(1L)
                .makerUid("x")
                .build();

        GroupJpaEntity saveGroupJpaEntity  = groupRepository.save(groupJpaEntity);

        //when
        GroupJpaEntity result = groupRepository.findById(saveGroupJpaEntity.getGid());

        //then
        assertThat(result.getGroupName()).isEqualTo("First Group");
        assertThat(result.getCalendarId()).isEqualTo(1L);
        assertThat(result.getMakerUid()).isEqualTo("x");
    }

    @Test
    @DisplayName("그룹 calendarId 조회")
    void findByCalendarId(){
        //given
        GroupJpaEntity groupJpaEntity = GroupJpaEntity.builder()
                .groupName("First Group")
                .calendarId(1L)
                .makerUid("x")
                .build();

        GroupJpaEntity saveGroupJpaEntity  = groupRepository.save(groupJpaEntity);

        //when
        GroupJpaEntity result = groupRepository.findByCalendarId(saveGroupJpaEntity.getCalendarId());

        //then
        assertThat(result.getGroupName()).isEqualTo("First Group");
        assertThat(result.getCalendarId()).isEqualTo(1L);
        assertThat(result.getMakerUid()).isEqualTo("x");
    }

    @Test
    @DisplayName("그룹 삭제")
    void deleteByGid(){
        //given
        GroupJpaEntity groupJpaEntity = GroupJpaEntity.builder()
                .groupName("First Group")
                .calendarId(1L)
                .makerUid("x")
                .build();

        GroupJpaEntity saveGroupJpaEntity  = groupRepository.save(groupJpaEntity);

        //when
        groupRepository.deleteById(saveGroupJpaEntity.getCalendarId());
        GroupJpaEntity result = groupRepository.findById(saveGroupJpaEntity.getGid());

        //then
        assertThat(result).isEqualTo(null);
    }

}
