package project.Reservations.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Reservations.dto.ReservationDTO;
import project.Reservations.entities.Reservation;
import project.Reservations.exception.ResourceNotFoundException;
import project.Reservations.repository.ReservationRepository;
import project.Reservations.service.ReservationService;
import project.Users.entities.User;
import project.Users.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "reservationService")
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    /* Inyección de dependencias */
    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
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
    public ReservationDTO save(Long user_id, ReservationDTO reservationDTO) {
        Reservation reservation = mapEntity(reservationDTO);
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        reservation.setUser(user);
        Reservation newReservation = reservationRepository.save(reservation);
        return mapDTO(newReservation);

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

    /* MAPPER */
    // Convierte entidad a DTO
    private ReservationDTO mapDTO(Reservation reservation) {
        return modelMapper.map(reservation, ReservationDTO.class);
    }

    // Convierte de DTO a Entidad
    private Reservation mapEntity(ReservationDTO reservationDTO) {
        return modelMapper.map(reservationDTO, Reservation.class);
    }

}
