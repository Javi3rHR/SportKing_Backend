package project.Reservations.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import project.Reservations.entities.Reservation;
import project.Reservations.repository.ReservationRepository;
import project.Reservations.service.ReservationService;
import project.Users.entities.User;

import java.util.ArrayList;
import java.util.List;

@Service(value = "reservationService")
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> list = new ArrayList<>();
        reservationRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Reservation> findByUserUser_id(Long user_id) {
        List<Reservation> list = new ArrayList<>();
        reservationRepository.findByUserUser_id(user_id).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation newReservation = new Reservation();
        newReservation.setDate(reservation.getDate());
        newReservation.setUser(reservation.getUser());
        User user = reservation.getUser();
        newReservation.setUsername(user.getUsername());

        return reservationRepository.save(newReservation);
    }

    @Override
    public Reservation deleteById(Long id) {
        Reservation reservation = reservationRepository.findById(id).get();
        reservationRepository.deleteById(id);
        return reservation;
    }

//    @Override
//    public Reservation insertReservation(String reservation_date, Long court_id, Long time_interval_id, Long user_id, boolean paid) {
//        return reservationRepository.insertReservation(reservation_date, court_id, time_interval_id, user_id, paid);
//    }

}
