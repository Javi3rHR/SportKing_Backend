package project.Reservations.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.Reservations.dto.reservation.ReservationDto;
import project.Reservations.dto.reservation.ReservationResponseDto;
import project.Reservations.entities.Reservation;
import project.Reservations.exception.AppException;
import project.Reservations.exception.ResourceNotFoundException;
import project.Reservations.repository.ReservationRepository;
import project.Reservations.service.ReservationService;
import project.Users.entities.User;
import project.Users.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.*;
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

    /* #################### GET #################### */
    @Override
    public List<ReservationResponseDto> findAll() {
        try {
            List<Reservation> reservations = new ArrayList<>();
            reservationRepository.findAll().iterator().forEachRemaining(reservations::add);
            List<ReservationResponseDto> reservationResponse = reservations.stream().map(this::mapDTOResponse).collect(Collectors.toList());
            setUserDetailsWithoutUserID(reservationResponse);
            return reservationResponse;
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, "List of reservations not found.");
        }
    }

    @Override
    public Optional<Reservation> findById(Long reservation_id) {
        return Optional.ofNullable(reservationRepository.findById(reservation_id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservation_id)));
    }


    @Override
    public List<ReservationResponseDto> findByUserUserId(Long user_id) {
        try {
            List<Reservation> reservations = reservationRepository.findByUserUserId(user_id);
            List<ReservationResponseDto> reservationResponse = reservations.stream().map(this::mapDTOResponse).collect(Collectors.toList());
            setUserDetails(user_id, reservationResponse);
            return reservationResponse;
        } catch (Exception e) {
            throw new ResourceNotFoundException("User", "user_id", user_id);
        }
    }

    @Override
    public ReservationResponseDto findByIdAndUserUserId(Long reservation_id, Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "user_id", user_id));
        try {
            Reservation reservation = reservationRepository.findByIdAndUserUserId(reservation_id, user_id);

            ReservationResponseDto reservationResponse = mapDTOResponse(reservation);
            reservationResponse.setUser_id(user.getUser_id());
            reservationResponse.setUsername(user.getUsername());
            reservationResponse.setEmail(user.getEmail());
            reservationResponse.setPhone(user.getPhone());

            return reservationResponse;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Reservation", "reservation_id", reservation_id);
        }
    }

    @Override
    public List<ReservationResponseDto> findByCourtCourtId(Long court_id) {
        try {
            List<Reservation> reservations = reservationRepository.findByCourtCourtId(court_id);
            List<ReservationResponseDto> reservationResponse = reservations.stream().map(this::mapDTOResponse).collect(Collectors.toList());
            setUserDetailsWithoutUserID(reservationResponse);
            return reservationResponse;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Court", "court_id", court_id);
        }
    }

    /* #################### POST #################### */
    @Override
    public ReservationDto save(Long user_id, ReservationDto reservationDTO) {
        Reservation reservation = mapEntity(reservationDTO);
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user_id", user_id));
        reservation.setUser(user);
        // TODO comprobar que funciona y retocar una vez tenga datos
//        if (checkReservationAlreadyExists(reservation.getCourt().getCourt_id(), reservation.getDate(), reservation.getTime_interval().getStart_time())) {
//            throw new AppException(HttpStatus.BAD_REQUEST, "Reservation already exists.");
//        }
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        // Comprobar formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        try{
        calendar.setTime(reservation.getDate());
        sdf.format(calendar.getTime());
        reservation.setDate(calendar.getTime());
        }catch (Exception e){
            throw new RuntimeException("Date format is not correct.");
        }

        // Comprobar si la fecha es anterior a la actual
        if (!reservation.getDate().after(new Date()) && !reservation.getDate().equals(new Date())) {
            throw new RuntimeException("Date '"+reservation.getDate()+"' is not valid.");
        }

        Reservation newReservation = reservationRepository.save(reservation);
        return mapDTO(newReservation);
    }

    /* #################### DELETE #################### */
    @Override
    public void delete(Long user_id, Long reservation_id) {
        try {
            User user = userRepository.findById(user_id)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
            Reservation reservation = reservationRepository.findById(reservation_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservation_id));
            if (reservation.getUser().getUser_id() != user.getUser_id()) {
                System.out.println(reservation.getUser().getUser_id() + "--" + user.getUser_id());
                throw new RuntimeException("Reservation does not belong to user");
            } else {
                System.out.println(reservation.getUser().getUser_id() + "--" + user.getUser_id());
                reservationRepository.delete(reservation);
            }
        } catch (Exception e) {
            throw new RuntimeException("Reservation not deleted.");
        }
    }

    @Override
    public void deleteWithAdmin(Long reservation_id) {
        try {
            Reservation reservation = reservationRepository.findById(reservation_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservation_id));

            reservationRepository.delete(reservation);
        } catch (Exception e) {
            throw new RuntimeException("Reservation not deleted.");
        }
    }

    /* #################### PUT #################### */






    /* #################### MAPPER #################### */

    // Convierte entidad a DTO
    private ReservationDto mapDTO(Reservation reservation) {
        try {
            return modelMapper.map(reservation, ReservationDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Reservation not mapped.");
        }
    }

    private ReservationResponseDto mapDTOResponse(Reservation reservation) {
        try {
            return modelMapper.map(reservation, ReservationResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Reservation not mapped.");
        }
    }

    // Convierte de DTO a Entidad
    private Reservation mapEntity(ReservationDto reservationDTO) {
        try {
            return modelMapper.map(reservationDTO, Reservation.class);
        } catch (Exception e) {
            throw new RuntimeException("Reservation not mapped.");
        }
    }


    /* #################### SETTERS #################### */

    /* TODO Refactorizar para no tener que recorrer todas */
    /* Insertar los campos de User en ReservationResponse pasando user_id */
    private void setUserDetails(Long user_id, List<ReservationResponseDto> reservationResponse) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        for (ReservationResponseDto r : reservationResponse) {
            if (r.getUser_id() == user_id) {
                r.setUsername(user.getUsername());
                r.setEmail(user.getEmail());
                r.setPhone(user.getPhone());
            }
        }
    }

    /* Insertar los campos de User en ReservationResponse sin pasar user_id */
    private void setUserDetailsWithoutUserID(List<ReservationResponseDto> reservationResponse) {
        User user;
        for (ReservationResponseDto r : reservationResponse) {
            user = userRepository.findById(r.getUser_id()).orElseThrow(() -> new ResourceNotFoundException("User", "id", r.getUser_id()));
            Long user_id = user.getUser_id();
            r.setUsername(user.getUsername());
            r.setEmail(user.getEmail());
            r.setPhone(user.getPhone());
            setUserDetails(user_id, reservationResponse);
        }
    }

    /* #################### CHECK #################### */
    /* Comprueba si una pista está disponible para reservar */
    @Override
    public boolean checkReservationAlreadyExists(Long court_id, String reservation_date, String start_time) {
        try {
            List<Reservation> reservations = reservationRepository.findByCourtCourtIdAndDateAndTimeIntervalStartTime(court_id, reservation_date, start_time);
            return reservations.size() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Reservation not checked.");
        }
    }

//    public boolean checkReservationDateAfterToday(String reservation_date) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            Date date = sdf.parse(reservation_date);
//            Date today = new Date();
//            return date.after(today);
//        } catch (Exception e) {
//            throw new AppException(HttpStatus.BAD_REQUEST, "Reservation not checked.");
//        }
//    }
}
