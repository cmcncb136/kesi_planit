package com.kesi.planit.calendar.infrastructure;

import com.kesi.planit.calendar.domain.Calendar;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class CalendarJpaEntity {
    @Id
    @GeneratedValue
    private long id;

    //Calendar를 구현하는데 있어서 소유자를 명시할 필요가 있다.
    //방법1. Calendar에 소유자를 모두 표시한다.
    //방법2. GID를 만들고 Calendar에 부여한다. 이때 Group은 1명도 포함된다.

    //Todo. Group 설정 완료 후 구현
    public Calendar toModel(){
        return null;
    }

    public static CalendarJpaEntity from(Calendar calendar){
        return null;
    }
}
