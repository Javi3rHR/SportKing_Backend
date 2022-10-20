package project.Reservations.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.Reservations.dto.ReservationDto;
import project.Reservations.dto.ReservationResponseDto;
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

    /* #################### GET #################### */
    @Override
    public List<ReservationResponseDto> findAll() {
        try{
        List<Reservation> reservations = new ArrayList<>();
        reservationRepository.findAll().iterator().forEachRemaining(reservations::add);
        List<ReservationResponseDto> reservationResponse = reservations.stream().map(this::mapDTOResponse).collect(Collectors.toList());
            setUserDetailsWithoutUserID(reservationResponse);
            return reservationResponse;
        }catch (Exception e){
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
        try{
        List<Reservation> reservations = reservationRepository.findByUserUserId(user_id);
        List<ReservationResponseDto> reservationResponse = reservations.stream().map(this::mapDTOResponse).collect(Collectors.toList());
            setUserDetails(user_id, reservationResponse);
            return reservationResponse;
        }catch (Exception e){
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
        try{
        List<Reservation> reservations = reservationRepository.findByCourtCourtId(court_id);
        List<ReservationResponseDto> reservationResponse = reservations.stream().map(this::mapDTOResponse).collect(Collectors.toList());
            setUserDetailsWithoutUserID(reservationResponse);
            return reservationResponse;
        }catch (Exception e){
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
        Reservation newReservation = reservationRepository.save(reservation);
        return mapDTO(newReservation);
    }

    /* #################### DELETE #################### */
    @Override
    public void delete(Long user_id, Long reservation_id) {
        try{
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
        }catch (Exception e){
            throw new AppException(HttpStatus.BAD_REQUEST, "Reservation not deleted.");
        }
    }

    @Override
    public void deleteWithAdmin(Long reservation_id) {
        try{
        Reservation reservation = reservationRepository.findById(reservation_id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", reservation_id));

            reservationRepository.delete(reservation);
        }
        catch (Exception e){
            throw new AppException(HttpStatus.BAD_REQUEST, "Reservation not deleted.");
        }
    }

    /* #################### PUT #################### */






    /* #################### MAPPER #################### */

    // Convierte entidad a DTO
    private ReservationDto mapDTO(Reservation reservation) {
        try{
        return modelMapper.map(reservation, ReservationDto.class);
        }catch (Exception e){
            throw new AppException(HttpStatus.BAD_REQUEST, "Reservation not mapped.");
        }
    }

    private ReservationResponseDto mapDTOResponse(Reservation reservation) {
        try{
        return modelMapper.map(reservation, ReservationResponseDto.class);
        }catch (Exception e){
            throw new AppException(HttpStatus.BAD_REQUEST, "Reservation not mapped.");
        }
    }

    // Convierte de DTO a Entidad
    private Reservation mapEntity(ReservationDto reservationDTO) {
        try{
        return modelMapper.map(reservationDTO, Reservation.class);
        }catch (Exception e){
            throw new AppException(HttpStatus.BAD_REQUEST, "Reservation not mapped.");
        }
    }


    /* #################### SETTERS #################### */

    /* TODO Refactorizar para no tener que recorrer todas */
    /* Insertar los campos de User en ReservationResponse pasando user_id */
    private void setUserDetails(Long user_id, List<ReservationResponseDto> reservationResponse) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        for(ReservationResponseDto r : reservationResponse){
            if(r.getUser_id() == user_id){
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

}
