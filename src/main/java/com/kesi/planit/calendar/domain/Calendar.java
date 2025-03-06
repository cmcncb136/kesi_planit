package com.kesi.planit.calendar.domain;

import com.kesi.planit.schedule.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@Builder
public class Calendar {
    private final Long id;


    public void updateCalendar(Schedule schedule) {

    }

    public void addSchedule(Schedule schedule) {

    }

    public void removeSchedule(Schedule schedule) {

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
