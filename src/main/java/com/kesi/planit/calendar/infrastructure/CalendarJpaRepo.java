package com.kesi.planit.calendar.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarJpaRepo extends JpaRepository<CalendarJpaEntity, Long> {
}
