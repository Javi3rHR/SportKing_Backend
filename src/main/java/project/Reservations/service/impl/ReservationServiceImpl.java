package project.Reservations.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Reservations.entities.Reservation;
import project.Reservations.repository.ReservationRepository;
import project.Reservations.service.ReservationService;

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
    public List<Reservation> findAllByUserId(Long user_id) {
        List<Reservation> list = new ArrayList<>();
        reservationRepository.findAllByUserId(user_id).iterator().forEachRemaining(list::add);
        return list;
    }

}
