package project.Reservations.service;

import project.Reservations.dto.timeInterval.TimeIntervalDto;

import java.util.List;

public interface TimeIntervalService {
    TimeIntervalDto findById(Long time_interval_id);

    /* Buscar intervalo de tiempo por court_id y reservation_date*/
    List<TimeIntervalDto> findByCourtCourtIdAndReservationDate(Long court_id, String reservation_date);

    //    List<TimeInterval> findAvailableTimeInterval(Long court_id, String reservation_date, String start_time);
    TimeIntervalDto save(Long court_id, TimeIntervalDto timeIntervalDto);

    //    String calculateEndTime(String start_time, int duration);
    void delete(Long time_interval_id);
}
