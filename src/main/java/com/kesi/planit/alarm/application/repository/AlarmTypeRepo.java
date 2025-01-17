package com.kesi.planit.alarm.application.repository;

public interface AlarmTypeRepo<T> {
    T findById(Long id);
    T findByAlarmId(Long alarmId);
    T save(T t);
    void deleteById(Long id);
}