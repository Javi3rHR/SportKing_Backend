package project.Reservations.service;

import project.Reservations.entities.Reservation;

import java.util.List;


public interface ReservationService {
    List<Reservation> findAll();
//    List<Reservation> findAllByUserId(Long user_id);
}
