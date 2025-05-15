package com.kesi.planit.schedule;

import com.kesi.planit.schedule.application.repository.ScheduleRepo;
import com.kesi.planit.schedule.infrastructure.ScheduleJpaEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@Transactional
public class ScheduleRepositoryTest {
    @Autowired
    private ScheduleRepo scheduleRepository;

    @Test
    @DisplayName("스케줄 생성")
    void createScheduleTest() {
        //given
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .description("test")
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .endTime(LocalTime.now().plusHours(10))
                .startTime(LocalTime.now())
                .colorValue(0xFFFF0000)
                .guestEditPermission(true)
                .build();

        //when
        ScheduleJpaEntity result = scheduleRepository.save(scheduleJpaEntity);

        //then
        assertThat(result.getClass()).isEqualTo(ScheduleJpaEntity.class);
        assertThat(result.getDescription()).isEqualTo("test");
        assertThat(result.getEndDate()).isEqualTo(LocalDate.now());
        assertThat(result.getStartDate()).isEqualTo(LocalDate.now());

        //time은 아주 시간 지난도 달아집으로 객체에 상태와 비교
        assertThat(result.getEndTime()).isEqualTo(scheduleJpaEntity.getEndTime());
        assertThat(result.getStartTime()).isEqualTo(scheduleJpaEntity.getStartTime());
        assertThat(result.getColorValue()).isEqualTo("FFFFFFFF");
        assertThat(result.getGuestEditPermission()).isEqualTo(true);
    }

    @Test
    @DisplayName("스케줄 조회")
    void findById() {
        //given
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .description("test")
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .endTime(LocalTime.now().plusHours(10))
                .startTime(LocalTime.now())
                .colorValue(0x0000FF00)
                .guestEditPermission(true)
                .build();

        ScheduleJpaEntity savedSchedule = scheduleRepository.save(scheduleJpaEntity);


        //when
        ScheduleJpaEntity result = scheduleRepository.findById(savedSchedule.getId());

        //then
        assertThat(result.getColorValue()).isEqualTo(savedSchedule.getColorValue());
        assertThat(result.getDescription()).isEqualTo(savedSchedule.getDescription());
        assertThat(result.getEndDate()).isEqualTo(savedSchedule.getEndDate());
        assertThat(result.getStartDate()).isEqualTo(savedSchedule.getStartDate());
        assertThat(result.getEndTime()).isEqualTo(savedSchedule.getEndTime());
        assertThat(result.getStartTime()).isEqualTo(savedSchedule.getStartTime());
        assertThat(result.getEndTime()).isEqualTo(savedSchedule.getEndTime());
        assertThat(result.getStartTime()).isEqualTo(savedSchedule.getStartTime());
    }

    @Test
    @DisplayName("스케줄 수정")
    void update(){
        //given
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .title("test")
                .description("test")
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .endTime(LocalTime.now().plusHours(10))
                .startTime(LocalTime.now())
                .colorValue(0xFF0000FF)
                .guestEditPermission(true)
                .build();

        ScheduleJpaEntity savedSchedule = scheduleRepository.save(scheduleJpaEntity);

        //when
        ScheduleJpaEntity updateSchedule = ScheduleJpaEntity.builder()
                .id(savedSchedule.getId())
                .title("update test")
                .description(savedSchedule.getDescription())
                .makerUid(savedSchedule.getMakerUid())
                .colorValue(0xFF0000FF)
                .endDate(savedSchedule.getEndDate())
                .startDate(savedSchedule.getStartDate())
                .endTime(savedSchedule.getEndTime())
                .startTime(savedSchedule.getStartTime())
                .guestEditPermission(savedSchedule.getGuestEditPermission())
                .build();

        ScheduleJpaEntity result = scheduleRepository.save(updateSchedule);

        //then
        assertThat(result.getId()).isEqualTo(updateSchedule.getId());
        assertThat(result.getTitle()).isEqualTo(updateSchedule.getTitle());
        assertThat(result.getDescription()).isEqualTo(updateSchedule.getDescription());
        assertThat(result.getMakerUid()).isEqualTo(updateSchedule.getMakerUid());
        assertThat(result.getColorValue()).isEqualTo(updateSchedule.getColorValue());
        assertThat(result.getEndDate()).isEqualTo(updateSchedule.getEndDate());
        assertThat(result.getStartDate()).isEqualTo(updateSchedule.getStartDate());
        assertThat(result.getEndTime()).isEqualTo(updateSchedule.getEndTime());
        assertThat(result.getStartTime()).isEqualTo(updateSchedule.getStartTime());
        assertThat(result.getGuestEditPermission()).isEqualTo(updateSchedule.getGuestEditPermission());
    }

    @Test
    @DisplayName("스케줄 삭제")
    void deleteById(){
        //given
        ScheduleJpaEntity scheduleJpaEntity = ScheduleJpaEntity.builder()
                .title("test")
                .description("test")
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .endTime(LocalTime.now().plusHours(10))
                .startTime(LocalTime.now())
                .colorValue(0xFFFFFFFF)
                .guestEditPermission(true)
                .build();

        ScheduleJpaEntity savedSchedule = scheduleRepository.save(scheduleJpaEntity);

        //when
        scheduleRepository.deleteById(scheduleJpaEntity.getId());
        ScheduleJpaEntity result = scheduleRepository.findById(savedSchedule.getId());

        //then
        assertThat(result).isEqualTo(null);
    }

    @Test
    @DisplayName("스케줄 리스트 반환 확인")
    void findAll(){
        //given
        ScheduleJpaEntity scheduleJpaEntity1 = ScheduleJpaEntity.builder()
                .title("test")
                .description("test")
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .endTime(LocalTime.now().plusHours(10))
                .startTime(LocalTime.now())
                .colorValue(0xFF00FFFF)
                .guestEditPermission(true)
                .build();

        ScheduleJpaEntity scheduleJpaEntity2 = ScheduleJpaEntity.builder()
                .title("test")
                .description("test")
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .endTime(LocalTime.now().plusHours(10))
                .startTime(LocalTime.now())
                .colorValue(0xFFFFFF00)
                .guestEditPermission(true)
                .build();

        scheduleRepository.save(scheduleJpaEntity1);
        scheduleRepository.save(scheduleJpaEntity2);

        //when
//        List<ScheduleJpaEntity> result = scheduleRepository.findAll();
//
//        //then
//        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("캘린더에 월 스케줄 조회")
    void findBySourceCalendarIAndDateRangeTest(){
        //given
        Long sourceCalendarId = 1L;

        for(int i = 1; i <= 12; i++){ //매달
            for(int j = 1; j <= 2; j++){ //2개씩 일정을 추가
                scheduleRepository.save(
                        ScheduleJpaEntity.builder()
                                .title("test")
                                .description("test")
                                .endDate(LocalDate.of(2025, i, j))
                                .startDate(LocalDate.of(2025, i, j))
                                .endTime(LocalTime.of(10, 30))
                                .startTime(LocalTime.of(10, 0))
                                .colorValue(0xFFFFFFFF)
                                .guestEditPermission(true)
                                .sourceCalendarId(sourceCalendarId)
                                .build()
                );
            }
        }

        LocalDate date = LocalDate.of(2025, 1, 1);

        //when
        //1월 데이터 조회
        List<ScheduleJpaEntity> scheduleJpaEntityList
                = scheduleRepository.findBySourceCalendarIdDateRange(sourceCalendarId
                , date
                , LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth()));

        //then
        assertThat(scheduleJpaEntityList.size()).isEqualTo(2); //2개만 존재해야됨
    }
}
