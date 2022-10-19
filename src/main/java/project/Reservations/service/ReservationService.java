package project.Reservations.service;

import project.Reservations.dto.ReservationDTO;
import project.Reservations.entities.Reservation;

import java.util.List;
import java.util.Optional;


public interface ReservationService {
    List<Reservation> findAll();

    List<Reservation> findByUserUserId(Long user_id);
    ReservationDTO save(Long user_id, ReservationDTO ReservationDTO);

    void delete(Long reservation_id);

    Optional<Reservation> findByIdAndUserUserId(Long reservation_id, Long user_id);


    Optional<Reservation> findById(Long reservation_id);
}
