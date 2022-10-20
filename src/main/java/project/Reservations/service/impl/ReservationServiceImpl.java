package project.Reservations.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.Reservations.dto.ReservationDto;
import project.Reservations.dto.ReservationResponse;
import project.Reservations.entities.Reservation;
import project.Reservations.exception.AppException;
import project.Reservations.exception.ResourceNotFoundException;
import project.Reservations.repository.ReservationRepository;
import project.Reservations.service.ReservationService;
import project.Users.entities.User;
import project.Users.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "reservationService")
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    /* Inyección de dependencias */
    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> list = new ArrayList<>();
        reservationRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<ReservationResponse> findByUserUserId(Long user_id) {

        List<Reservation> reservations = reservationRepository.findByUserUserId(user_id);
        List<ReservationResponse> reservationResponse = reservations.stream().map(this::mapDTOResponse).collect(Collectors.toList());

        /* TODO Refactorizar para no tener que recorrer todas*/
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        for(ReservationResponse r : reservationResponse){
            if(r.getUser_id() == user_id){
                r.setUsername(user.getUsername());
                r.setEmail(user.getEmail());
                r.setPhone(user.getPhone());
            }
        }
        return reservationResponse;

    }

    @Override
    public Optional<Reservation> findById(Long reservation_id) {
        return reservationRepository.findById(reservation_id);

    }

    @Override
    public Optional<Reservation> findByIdAndUserUserId(Long reservation_id, Long user_id) {
        return Optional.ofNullable(reservationRepository.findByIdAndUserUserId(reservation_id, user_id));
    }

    @Override
    public ReservationDto save(Long user_id, ReservationDto reservationDTO) {
        Reservation reservation = mapEntity(reservationDTO);
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        reservation.setUser(user);
        Reservation newReservation = reservationRepository.save(reservation);
        return mapDTO(newReservation);

    }

    @Override
    public void delete(Long user_id, Long reservation_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        Reservation reservation = reservationRepository.findById(reservation_id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservation_id));
        if(reservation.getUser().getUser_id() != user.getUser_id()){
            System.out.println(reservation.getUser().getUser_id()+"--"+ user.getUser_id());
            throw new AppException(HttpStatus.BAD_REQUEST, "Reservation does not belong to user");
        }else{
            System.out.println(reservation.getUser().getUser_id()+"--"+ user.getUser_id());
            reservationRepository.delete(reservation);
        }
    }

    @Override
    public void deleteWithAdmin(Long reservation_id) {
        Reservation reservation = reservationRepository.findById(reservation_id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservation_id));

            reservationRepository.delete(reservation);
        }

//    @Override
//    public Reservation insertReservation(String reservation_date, Long court_id, Long time_interval_id, Long user_id, boolean paid) {
//        return reservationRepository.insertReservation(reservation_date, court_id, time_interval_id, user_id, paid);
//    }

    /* MAPPER */
    // Convierte entidad a DTO
    private ReservationDto mapDTO(Reservation reservation) {
        return modelMapper.map(reservation, ReservationDto.class);
    }

    private ReservationResponse mapDTOResponse(Reservation reservation) {
        return modelMapper.map(reservation, ReservationResponse.class);
    }

    // Convierte de DTO a Entidad
    private Reservation mapEntity(ReservationDto reservationDTO) {
        return modelMapper.map(reservationDTO, Reservation.class);
    }

}
