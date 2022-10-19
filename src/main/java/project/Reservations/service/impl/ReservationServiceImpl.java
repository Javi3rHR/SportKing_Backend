package project.Reservations.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.Reservations.entities.Reservation;
import project.Reservations.repository.ReservationRepository;
import project.Reservations.service.ReservationService;
import project.Users.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<Reservation> findById(Long reservation_id) {
        return reservationRepository.findById(reservation_id);

    }

    @Override
    public List<Reservation> findByUserUserId(Long user_id) {
        List<Reservation> list = new ArrayList<>();
        reservationRepository.findByUserUserId(user_id).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Optional<Reservation> findByIdAndUserUserId(Long reservation_id, Long user_id) {
        return Optional.ofNullable(reservationRepository.findByIdAndUserUserId(reservation_id, user_id));
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation newReservation = new Reservation();
        newReservation.setDate(reservation.getDate());
        newReservation.setUser(reservation.getUser());
        newReservation.setPaid(reservation.getPaid());
        User user = reservation.getUser();
        newReservation.setUsername(user.getUsername());

        return reservationRepository.save(newReservation);
    }

    @Override
    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation does not exist"));
        reservationRepository.delete(reservation);
    }

//    @Override
//    public Reservation insertReservation(String reservation_date, Long court_id, Long time_interval_id, Long user_id, boolean paid) {
//        return reservationRepository.insertReservation(reservation_date, court_id, time_interval_id, user_id, paid);
//    }

}
