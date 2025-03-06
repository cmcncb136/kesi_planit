package com.kesi.planit.schedule;

import com.kesi.planit.schedule.domain.SecurityLevel;
import com.kesi.planit.schedule.infrastructure.ScheduleJpaEntity;
import com.kesi.planit.schedule.infrastructure.ScheduleJpaRepo;
import com.kesi.planit.schedule.infrastructure.ScheduleSecurityEntity;
import com.kesi.planit.schedule.infrastructure.ScheduleSecurityJpaRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ScheduleSecurityRepositoryTest {
    @Autowired
    ScheduleSecurityJpaRepo scheduleSecurityJpaRepo;

    @Autowired
    ScheduleJpaRepo scheduleJpaRepo;

    @Test
    @DisplayName("유저와 연결될 스케줄 월별로 조회")
    void findSchedulesUidAndWithinDateRangeTest() {
        //given
        Long sourceCalendarId = 1L;
        String uid = "x";
        String uid2 = "x2";
        LocalDate date = LocalDate.of(2025, 1, 1);

        for (int i = 1; i <= 12; i++) { //매달
            for (int j = 1; j <= 2; j++) { //2개씩 일정을 추가
                ScheduleJpaEntity scheduleJpaEntity = scheduleJpaRepo.save(
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

                scheduleSecurityJpaRepo.save(ScheduleSecurityEntity.builder()
                        .scheduleId(scheduleJpaEntity.getId())
                        .securityLevel(SecurityLevel.MEDIUM)
                        .uid(uid)
                        .build());

                scheduleSecurityJpaRepo.save(ScheduleSecurityEntity.builder()
                        .scheduleId(scheduleJpaEntity.getId())
                        .securityLevel(SecurityLevel.MEDIUM)
                        .uid(uid2)
                        .build());
            }
        }

        //when
        //x유저 1월 데이터 조회
        List<ScheduleSecurityEntity> scheduleSecurityEntities
                = scheduleSecurityJpaRepo.findSchedulesUidAndWithinDateRange(
                        uid, date, LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth()));

        //then
        assertThat(scheduleSecurityEntities.size()).isEqualTo(2);
    }
}
