package project.Reservations.repository;

import org.springframework.data.repository.CrudRepository;
import project.Reservations.entities.TimeInterval;

public interface TimeIntervalRepository extends CrudRepository<TimeInterval, Long> {

//    List<TimeInterval> findAllByCourtCourtIdAndReservationDate(Long court_id, String reservation_date);
//    List<TimeInterval> findAllByCourtCourtIdAndReservationDateAndStartTime(Long court_id, String reservation_date, int start_time);
//    List<TimeInterval> findAllByCourtCourtIdAndCourtNameAndReservationDateAndStartTime(Long court_id, String court_name, String reservation_date, String start_time);
//    TimeInterval findByCourtCourtIdAndStartHour(Long court_id, int start_hour);
//    TimeInterval findByCourtCourtIdAndReservationDateAndStartHour(Long court_id, String reservation_date, int start_hour);
//    TimeInterval findByCourtCourtIdAndCourtNameAndReservationDateAndStartTime(Long court_id, String court_name, String reservation_date, String start_time);
}
