package com.kesi.planit.calendar.domain;

import com.kesi.planit.schedule.domain.ScheduleSource;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class Calendar {
    private final Long id;


    public void updateCalendar(ScheduleSource schedule) {

    }

    public void addSchedule(ScheduleSource schedule) {

    }

    public void removeSchedule(ScheduleSource schedule) {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Calendar calendar = (Calendar) o;
        return Objects.equals(id, calendar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
