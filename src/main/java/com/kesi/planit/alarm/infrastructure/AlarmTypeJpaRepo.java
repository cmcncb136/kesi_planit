package com.kesi.planit.alarm.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmTypeJpaRepo<T> extends JpaRepository<T, Long> {
    T findByAlarmId(Long alarmId);
}
