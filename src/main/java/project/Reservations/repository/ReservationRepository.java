package project.Reservations.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.Reservations.entities.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findAll();
    Reservation findById(long id);
    // InnerJoin para obtener las reservas de un usuario
    @Query(value = "SELECT reservation_date, court_id, time_interval_id, paid FROM Reservation WHERE user_id = :user_id", nativeQuery = true)
    List<Reservation> findAllByUserId(Long user_id);

    Reservation save(Reservation reservation);
}
