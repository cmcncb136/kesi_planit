package com.kesi.planit.group.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

//GroupJpaRepo로 하면 왜 인지는 모르겠으나 순환 참조가 일어남
//나중에 Spring Container에서 Bean를 어떻게 관리하는지 확인해봐야할 것 같다.
public interface GroupJpaRepository extends JpaRepository<GroupJpaEntity, Long> {
    GroupJpaEntity findByCalendarId(long calendarId);
}
