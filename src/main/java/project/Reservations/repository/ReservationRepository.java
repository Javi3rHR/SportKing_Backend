package project.Reservations.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.Reservations.entities.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    /* #### GET #### */

//    List<Reservation> findByUserUserId(Long user_id);

    @Query(value = "SELECT * FROM reservation WHERE court_id = :court_id", nativeQuery = true)
    List<Reservation> findByCourtCourtId(Long court_id);

    @Query(value = "SELECT reservation_date, court_id, time_interval_id, paid " +
            "FROM Reservation WHERE start_hour = :start_hour " +
            "AND reservation_date = :reservation_date " +
            "AND court_id = :court_id", nativeQuery = true)
    List<Reservation> findByCourtCourtIdAndDateAndTimeIntervalStartHour(Long court_id, String reservation_date, int start_hour);

//    Reservation findById(long id);
    // InnerJoin para obtener las reservas de un usuario
    @Query(value = "SELECT reservation_date, court_id, time_interval_id, paid " +
            "FROM Reservation " +
            "WHERE user_id = :user_id", nativeQuery = true)
    List<Reservation> findAllByUserId(Long user_id);

    @Query(value = "SELECT reservation_date, court_id, time_interval_id, paid " +
            "FROM Reservation WHERE start_hour = :start_hour " +
            "AND reservation_date = :reservation_date", nativeQuery = true)
    Reservation findByDateAndTimeIntervalStartHour(String reservation_date, int start_hour);


    /* #### DELETE #### */

//    @Modifying
//    @Query(value = "DELETE FROM Reservation WHERE reservation_id = :reservation_id", nativeQuery = true)
//    Reservation deleteById(long id);

    /* #### INSERT #### */

//    @Modifying
//    @Query(value = "INSERT INTO Reservation (user_id, court_id, time_interval_id, reservation_date, paid) " +
//            "VALUES (:user_id, :court_id, :time_interval_id, :reservation_date, :paid)", nativeQuery = true)
//    Reservation insertReservation(String reservation_date, Long court_id, Long time_interval_id, Long user_id, boolean paid);
}
