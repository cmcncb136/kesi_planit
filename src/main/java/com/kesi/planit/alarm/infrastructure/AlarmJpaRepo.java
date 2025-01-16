package com.kesi.planit.alarm.infrastructure;

import com.kesi.planit.alarm.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmJpaRepo extends JpaRepository<AlarmJpaEntity, Long > {
    List<AlarmJpaEntity> findByUid(String uid);
}
