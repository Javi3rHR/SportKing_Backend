package project.Reservations.service;

import project.Reservations.dto.reservation.ReservationDto;
import project.Reservations.dto.reservation.ReservationResponseDto;
import project.Reservations.entities.Reservation;

import java.util.List;
import java.util.Optional;


public interface ReservationService {
    List<ReservationResponseDto> findAll();

    List<ReservationResponseDto> findByUserUserId(Long user_id);

    List<ReservationResponseDto> findByCourtCourtId(Long court_id);

    /**
     * Busca una reserva por su id y el id del usuario que la ha hecho
     *
     * @param reservation_id
     * @param user_id
     * @return ReservationResponseDto
     */
    ReservationResponseDto findByIdAndUserUserId(Long reservation_id, Long user_id);

    //    ReservationResponseDto findByIdAndUserUsername(Long reservation_id, String username);
    Optional<Reservation> findById(Long reservation_id);

    ReservationDto save(Long user_id, ReservationDto ReservationDTO);

    void delete(Long user_id, Long reservation_id);

    void deleteWithAdmin(Long reservation_id);

}
