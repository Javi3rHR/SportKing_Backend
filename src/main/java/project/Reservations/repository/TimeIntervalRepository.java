package project.Reservations.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import project.Reservations.entities.TimeInterval;

public interface TimeIntervalRepository extends CrudRepository<TimeInterval, Long> {

    @Query(value = "SELECT 1 FROM time_interval " +
            "WHERE court_id = :court_id " +
            "AND start_time = :start_time " +
            "AND end_time = :end_time", nativeQuery = true)
    Boolean existsByCourtCourtIdAndStart_timeAndEnd_time(Long court_id, String start_time, String end_time);

//    List<TimeInterval> findAllByCourtCourtIdAndReservationDate(Long court_id, String reservation_date);
//    List<TimeInterval> findAllByCourtCourtIdAndReservationDateAndStartTime(Long court_id, String reservation_date, int start_time);
//    List<TimeInterval> findAllByCourtCourtIdAndCourtNameAndReservationDateAndStartTime(Long court_id, String court_name, String reservation_date, String start_time);
//    TimeInterval findByCourtCourtIdAndStartHour(Long court_id, int start_hour);
//    TimeInterval findByCourtCourtIdAndReservationDateAndStartHour(Long court_id, String reservation_date, int start_hour);
//    TimeInterval findByCourtCourtIdAndCourtNameAndReservationDateAndStartTime(Long court_id, String court_name, String reservation_date, String start_time);
}
