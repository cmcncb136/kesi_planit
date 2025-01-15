package com.kesi.planit.alarm.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AlarmTypeJpaRepo<T> extends JpaRepository<T, Long> {
    T findByAlarmId(Long alarmId);
}
