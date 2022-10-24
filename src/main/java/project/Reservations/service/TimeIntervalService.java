package project.Reservations.service;

import project.Reservations.entities.TimeInterval;

import java.util.List;

public interface TimeIntervalService {
    TimeInterval findById(Long time_interval_id);
    List<TimeInterval> findAvailableTimeInterval(Long court_id, String reservation_date, String start_time);
    List<TimeInterval> createTimeInterval(Long court_id, String reservation_date);
    String calculateEndTime(String start_time, int duration);
    boolean ifHalfHour(String start_time);
    void setCourtToTimeInterval(Long court_id);
}
