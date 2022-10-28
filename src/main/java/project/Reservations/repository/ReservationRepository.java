package project.Reservations.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.Reservations.entities.Reservation;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    /* #### GET #### */

//    List<Reservation> findByUserUserId(Long user_id);

    @Query(value = "SELECT * FROM reservation WHERE court_id = :court_id", nativeQuery = true)
    List<Reservation> findByCourtCourtId(Long court_id);

    /**
     * Busca una reserva por la fecha de la reserva, el id de la pista y la hora de inicio.
     * @param court_id
     * @param reservation_date
     * @param start_time
     * @return Reservation
     */
    @Query(value = "SELECT * " +
            "FROM reservation r" +
            "INNER JOIN time_interval t" +
            "ON r.time_interval_id = t.time_interval_id" +
            "WHERE r.court_id = :court_id AND r.reservation_date = :reservation_date AND t.start_time = :start_time", nativeQuery = true)
    Object findByCourtCourtIdAndDateAndTimeIntervalStartTime(Long court_id, String reservation_date, String start_time);

    @Query(value = "SELECT * " +
            "FROM reservation " +
            "WHERE reservation_date = :reservation_date " +
            "AND time_interval_id = :time_interval_id ", nativeQuery = true)
    Object checkIfReservationExists(Date reservation_date, Long time_interval_id);

    @Query(value = "SELECT * FROM reservation WHERE user_id = :user_id", nativeQuery = true)
    List<Reservation> findByUserUserId(Long user_id);

    @Query(value = "SELECT * FROM reservation " +
            "WHERE reservation_id = :reservation_id " +
            "AND user_id = :user_id", nativeQuery = true)
    Reservation findByIdAndUserUserId(Long reservation_id, Long user_id);

    @Query(value = "SELECT * FROM reservation " +
            "WHERE reservation_id = :reservation_id " +
            "AND username = :username", nativeQuery = true)
    Reservation findByIdAndUserUsername(Long reservation_id, String username);

//    @Query(value = "SELECT reservation_date, court_id, time_interval_id, paid " +
//            "FROM Reservation WHERE start_time = :start_time " +
//            "AND reservation_date = :reservation_date", nativeQuery = true)
//    Reservation findByDateAndTimeIntervalStartHour(String reservation_date, int start_time);

    /* Reservas pagadas */
    @Query(value = "SELECT * FROM reservation WHERE paid = true", nativeQuery = true)
    List<Reservation> findByPaidIsTrue(boolean paid);

    /* Reservas pendientes de pagar */
    @Query(value = "SELECT * FROM reservation WHERE paid = false", nativeQuery = true)
    List<Reservation> findByPaidIsFalse(boolean paid);

}
