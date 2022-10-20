package project.Reservations.service;

import project.Reservations.dto.ReservationDto;
import project.Reservations.dto.ReservationResponseDto;
import project.Reservations.entities.Reservation;

import java.util.List;
import java.util.Optional;


public interface ReservationService {
    List<ReservationResponseDto> findAll();
    List<ReservationResponseDto> findByUserUserId(Long user_id);
    List<ReservationResponseDto> findByCourtCourtId(Long court_id);
    ReservationDto save(Long user_id, ReservationDto ReservationDTO);
    void delete(Long user_id, Long reservation_id);
    void deleteWithAdmin(Long reservation_id);
    ReservationResponseDto findByIdAndUserUserId(Long reservation_id, Long user_id);
    Optional<Reservation> findById(Long reservation_id);
}
