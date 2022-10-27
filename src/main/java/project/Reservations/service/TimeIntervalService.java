package project.Reservations.service;

import project.Reservations.dto.timeInterval.TimeIntervalDto;

public interface TimeIntervalService {
    TimeIntervalDto findById(Long time_interval_id);
//    List<TimeInterval> findAvailableTimeInterval(Long court_id, String reservation_date, String start_time);
    TimeIntervalDto save(Long court_id, TimeIntervalDto timeIntervalDto);
//    String calculateEndTime(String start_time, int duration);
}
