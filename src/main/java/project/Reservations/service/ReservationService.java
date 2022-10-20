package project.Reservations.service;

import project.Reservations.dto.ReservationDto;
import project.Reservations.dto.ReservationResponse;
import project.Reservations.entities.Reservation;

import java.util.List;
import java.util.Optional;


public interface ReservationService {
    List<ReservationResponse> findAll();

    List<ReservationResponse> findByUserUserId(Long user_id);
    ReservationDto save(Long user_id, ReservationDto ReservationDTO);

    void delete(Long user_id, Long reservation_id);
    void deleteWithAdmin(Long reservation_id);

    ReservationResponse findByIdAndUserUserId(Long reservation_id, Long user_id);


    Optional<Reservation> findById(Long reservation_id);
}
