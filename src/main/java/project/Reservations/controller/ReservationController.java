package project.Reservations.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.Reservations.dto.reservation.ReservationDto;
import project.Reservations.dto.reservation.ReservationResponseAdminDto;
import project.Reservations.dto.reservation.ReservationResponseDto;
import project.Reservations.service.ReservationService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    /* #################### GET #################### */
    // TODO RESTA UN DIA DE LA FECHA QUE SÉ LE PASA Y NO SE POR QUÉ
    @GetMapping("/reservations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationResponseAdminDto>> getAll() {
        return new ResponseEntity<>(reservationService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/users/{user_id}/reservations")
    @PreAuthorize("hasRole('ADMIN') or @authenticatedUserService.hasId(#user_id)")
    public ResponseEntity<List<ReservationResponseDto>> getAllByUserId(@PathVariable("user_id") Long user_id) {
        return new ResponseEntity<>(reservationService.findByUserUserId(user_id), HttpStatus.OK);
    }

    @GetMapping("/users/{user_id}/reservations/{reservation_id}")
    @PreAuthorize("hasRole('ADMIN') or @authenticatedUserService.hasId(#user_id)")
    public ResponseEntity<ReservationResponseDto> getByIdAndUserId(@PathVariable("user_id") Long user_id, @PathVariable("reservation_id") Long reservation_id) {
        return new ResponseEntity<>(reservationService.findByIdAndUserUserId(reservation_id, user_id), HttpStatus.OK);
    }


    /* #################### POST #################### */
    @PostMapping("/users/{user_id}/reservations")
    @PreAuthorize("hasRole('ADMIN') or @authenticatedUserService.hasId(#user_id)")
    public ResponseEntity<ReservationDto> makeReservation(@PathVariable("user_id") Long user_id, @Valid @RequestBody ReservationDto reservationDTO) {
        return new ResponseEntity<>(reservationService.save(user_id, reservationDTO), HttpStatus.CREATED);
    }


    /* #################### DELETE #################### */

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/reservations/{reservation_id}")
    public ResponseEntity<String> deleteWithAdmin(@PathVariable(value = "reservation_id") Long reservation_id) {
        reservationService.deleteWithAdmin(reservation_id);
        return new ResponseEntity<>("Reservation with id '" + reservation_id + "' has been deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or @authenticatedUserService.hasId(#user_id)")
    @DeleteMapping("/users/{user_id}/reservations/{reservation_id}")
    public ResponseEntity<String> cancelReservation(@PathVariable(value = "user_id") Long user_id, @PathVariable(value = "reservation_id") Long reservation_id) {
        reservationService.delete(user_id, reservation_id);
        return new ResponseEntity<>("Reservation with id '" + reservation_id + "' has been deleted", HttpStatus.OK);
    }
}
