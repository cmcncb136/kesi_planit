package com.kesi.planit.calendar;

import com.kesi.planit.calendar.application.repository.CalendarRepo;
import com.kesi.planit.calendar.infrastructure.CalendarJpaEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class CalendarRepositoryTest {
    @Autowired
    private CalendarRepo calendarRepository;

    //Todo. 추후 캘린더 객체 구현 후 생성
    @Test
    @DisplayName("캘린더 생성")
    void createCalendar() {
        //given
        //CalendarJpaEntity calendarJpaEntity = CalendarJpaEntity.

        //when

        //then
    }

}
