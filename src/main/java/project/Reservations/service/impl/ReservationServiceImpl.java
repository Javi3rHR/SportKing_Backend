package project.Reservations.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.Reservations.dto.reservation.ReservationDto;
import project.Reservations.dto.reservation.ReservationResponseDto;
import project.Reservations.entities.Reservation;
import project.Reservations.exception.ResourceNotFoundException;
import project.Reservations.repository.ReservationRepository;
import project.Reservations.repository.TimeIntervalRepository;
import project.Reservations.service.ReservationService;
import project.Users.entities.User;
import project.Users.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service(value = "reservationService")
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;

    private final TimeIntervalRepository timeIntervalRepository;

    private final ModelMapper modelMapper;

    /* Inyección de dependencias */
    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, TimeIntervalRepository timeIntervalRepository, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.timeIntervalRepository = timeIntervalRepository;
        this.modelMapper = modelMapper;
    }


    /* #################### GET #################### */

    /* Busca todas las reservas */
    @Override
    public List<ReservationResponseDto> findAll() {
        try {
            List<Reservation> reservations = new ArrayList<>();
            reservationRepository.findAll().iterator().forEachRemaining(reservations::add);
            List<ReservationResponseDto> reservationResponse = reservations.stream().map(this::mapDTOResponse).collect(Collectors.toList());
            setUserDetailsWithoutUserID(reservationResponse);
            return reservationResponse;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List of reservations not found.");
        }
    }

    /* Busca una reserva por su id */
    @Override
    public Optional<Reservation> findById(Long reservation_id) {
        return Optional.ofNullable(reservationRepository.findById(reservation_id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservation_id)));
    }

    /* Busca las reservas de un usuario */
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

    /* Busca una reserva por su id y el id del usuario */
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

    /* Busca las reservas de una pista */
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

    /* Crea una reserva */
    @Override
    public ReservationDto save(Long user_id, ReservationDto reservationDTO) {

        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user_id", user_id));

        Reservation reservation = mapEntity(reservationDTO);

        // Formateo de fechas
        SimpleDateFormat sdf = getSimpleDateFormat(reservation);

        // Generar fecha con formato correcto para la validación
        Date reservationDateDate = getDate(reservation, sdf);

        // Validar que la reserva no se solape con otra
        checkReservationAlreadyExist(reservationDTO, reservationDateDate);

        // Setters
        reservation.setUser(user);
        reservation.setTime_interval(timeIntervalRepository.findById(reservationDTO.getTime_interval_id())
                .orElseThrow(() -> new ResourceNotFoundException("Time interval", "time_interval_id", reservationDTO.getTime_interval_id())));

        return mapDTO(reservationRepository.save(reservation));
    }


    /* #################### DELETE #################### */

    /* Borra una reserva */
    @Override
    public void delete(Long user_id, Long reservation_id) {

        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));

        Reservation reservation = reservationRepository.findById(reservation_id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservation_id));

        // Comprobar si la reserva pertenece al usuario
        if (reservation.getUser().getUser_id() != user.getUser_id()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation does not belong to user");
        }

        // No se puede eliminar cuando quedan menos de 24 horas para la reserva
        Date today = new Date();
        Date reservationDate = reservation.getDate();
        Date reservationDateMinusOneDay = new Date(reservationDate.getTime() - 86400000);

        if (reservationDateMinusOneDay.before(today)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Reservation cannot be deleted when there are less than 1 day left for the reservation.");
        }

        try {
            reservationRepository.delete(reservation);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not deleted.");
        }
    }

    /* Borra una reserva sin necesidad de pasar el id del usuario */
    @Override
    public void deleteWithAdmin(Long reservation_id) {
        try {
            Reservation reservation = reservationRepository.findById(reservation_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservation_id));

            reservationRepository.delete(reservation);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not deleted.");
        }
    }


    /* #################### PUT #################### */


    /* #################### MAPPER #################### */

    // Convierte entidad a DTO
    private ReservationDto mapDTO(Reservation reservation) {
        try {
            return modelMapper.map(reservation, ReservationDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not mapped.");
        }
    }

    private ReservationResponseDto mapDTOResponse(Reservation reservation) {
        try {
            return modelMapper.map(reservation, ReservationResponseDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not mapped.");
        }
    }

    // Convierte de DTO a Entidad
    private Reservation mapEntity(ReservationDto reservationDTO) {
        try {
            return modelMapper.map(reservationDTO, Reservation.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not mapped.");
        }
    }


    /* #################### SETTERS #################### */

    /* TODO Refactorizar para no tener que recorrer todas */

    /**
     * Setea los detalles del usuario en cada reserva de la lista para devolverlo en el response
     *
     * @param user_id
     * @param reservationResponse
     */
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

    /**
     * Insertar los campos de User en ReservationResponse sin pasar user_id
     *
     * @param reservationResponse
     */
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


    /* #################### VALIDATIONS #################### */

    /**
     * Generar fecha con formato correcto para su validaci'on
     *
     * @param reservation
     * @param sdf
     * @return Date
     */
    private static Date getDate(Reservation reservation, SimpleDateFormat sdf) {
        String today = sdf.format(new Date());
        String reservationDate = sdf.format(reservation.getDate());
        Date todayDate;
        Date reservationDateDate;

        try {
            todayDate = sdf.parse(today);
            reservationDateDate = sdf.parse(reservationDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Comprobar que la fecha de la reserva es posterior a la fecha actual
        if (reservationDateDate.before(todayDate)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation date '" + reservationDate + "' must be after today's date '" + today + "'.");
        }
        return reservationDateDate;
    }

    /**
     * Establece el formato para la fecha
     *
     * @param reservation
     * @return SimpleDateFormat
     */
    private static SimpleDateFormat getSimpleDateFormat(Reservation reservation) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(reservation.getDate());
            sdf.format(calendar.getTime());
            reservation.setDate(calendar.getTime());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Date format is not correct.");
        }
        return sdf;
    }

    /**
     * Comprueba si la reserva ya existe
     *
     * @param reservationDTO
     * @param reservationDateDate
     */
    private void checkReservationAlreadyExist(ReservationDto reservationDTO, Date reservationDateDate) {
        Long time_interval_id = reservationDTO.getTime_interval_id();
        // Comprobar que la pista no está reservada en ese horario
        if ((reservationRepository.checkIfReservationExists(
                reservationDateDate,
                time_interval_id)) != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Court is already reserved in that time interval.");
        }
    }

}
