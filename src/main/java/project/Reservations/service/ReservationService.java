package project.Reservations.service;

import project.Reservations.entities.Reservation;

import java.util.List;
import java.util.Optional;


public interface ReservationService {
    List<Reservation> findAll();

    List<Reservation> findByUserUserId(Long user_id);
    Reservation save(Reservation reservation);

    void delete(Long reservation_id);

    Optional<Reservation> findByIdAndUserUserId(Long reservation_id, Long user_id);


}
