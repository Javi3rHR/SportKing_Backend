package project.Reservations.repository;

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

    // InnerJoin con TimeInterval para sacar start_time
    @Query(value = "SELECT court_id, reservation_date, start_time " +
            "FROM reservation " +
            "INNER JOIN time_interval" +
            "ON time_interval_id.reservation = time_interval_id.time_interval", nativeQuery = true)
    List<Reservation> findByCourtCourtIdAndDateAndTimeIntervalStartTime(Long court_id, String reservation_date, String start_time);

    @Query(value = "SELECT * FROM reservation WHERE user_id = :user_id", nativeQuery = true)
    List<Reservation> findByUserUserId(Long user_id);

    @Query(value = "SELECT * FROM reservation " +
            "WHERE reservation_id = :reservation_id " +
            "AND user_id = :user_id", nativeQuery = true)
    Reservation findByIdAndUserUserId(Long reservation_id, Long user_id);

    @Query(value = "SELECT reservation_date, court_id, time_interval_id, paid " +
            "FROM Reservation WHERE start_time = :start_time " +
            "AND reservation_date = :reservation_date", nativeQuery = true)
    Reservation findByDateAndTimeIntervalStartHour(String reservation_date, int start_time);

    /* Reservas pagadas */
    @Query(value = "SELECT * FROM reservation WHERE paid = true", nativeQuery = true)
    List<Reservation> findByPaidIsTrue(boolean paid);

    /* Reservas pendientes de pagar */
    @Query(value = "SELECT * FROM reservation WHERE paid = false", nativeQuery = true)
    List<Reservation> findByPaidIsFalse(boolean paid);


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
