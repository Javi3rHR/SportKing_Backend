package project.Reservations.service;

import project.Reservations.entities.Reservation;

import java.util.List;


public interface ReservationService {
    List<Reservation> findAll();

    List<Reservation> findByUserUser_id(Long user_id);
    Reservation save(Reservation reservation);

    Reservation deleteById(Long id);

}
