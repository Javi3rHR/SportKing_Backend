package project.Reservations.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.Reservations.dto.ReservationDto;
import project.Reservations.dto.ReservationResponse;
import project.Reservations.entities.Reservation;
import project.Reservations.service.ReservationService;
import project.Users.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    private final UserService userService;

    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }


    /* ########## GET ########## */
    @GetMapping("/reservations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reservation>> getAll() {
        if (reservationService.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservationService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/users/{user_id}/reservations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ReservationResponse>> getAllByUserId(@PathVariable("user_id") Long user_id) {
        return new ResponseEntity<>(reservationService.findByUserUserId(user_id), HttpStatus.OK);
    }

    @GetMapping("/users/{user_id}/reservations/{reservation_id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Reservation> getByIdAndUserId(@PathVariable("user_id") Long user_id, @PathVariable("reservation_id") Long reservation_id) {
        return reservationService.findByIdAndUserUserId(reservation_id, user_id)
                .map(reservation -> new ResponseEntity<>(reservation, HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }


    /* ########## POST ########## */

    @PostMapping("/users/{user_id}/reservations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ReservationDto> saveByUserId(@PathVariable("user_id") Long user_id, @Valid @RequestBody ReservationDto reservationDTO) {
        return new ResponseEntity<>(reservationService.save(user_id, reservationDTO), HttpStatus.CREATED);
    }


    /* ########## DELETE ########## */

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/reservations/{reservation_id}")
    public ResponseEntity<String> deleteWithAdmin(@PathVariable(value = "reservation_id") Long reservation_id) {
        reservationService.deleteWithAdmin(reservation_id);
        return new ResponseEntity<>("Reservation with id '"+reservation_id+"' has been deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/users/{user_id}/reservations/{reservation_id}")
    public ResponseEntity<String> delete(@PathVariable(value = "user_id") Long user_id, @PathVariable(value = "reservation_id") Long reservation_id) {
        reservationService.delete(user_id, reservation_id);
        return new ResponseEntity<>("Reservation with id '"+reservation_id+"' has been deleted", HttpStatus.OK);
    }


    /* ########## PUT ########## */
//    @PutMapping("/users/{user_id}/reservations/{reservation_id}")
//    public ResponseEntity<Reservation> update(@PathVariable(value = "user_id") Long user_id, @PathVariable(value = "reservation_id") Long reservation_id, @Valid @RequestBody Reservation reservationRequest) {
//        if (!userService.existsById(user_id)) {
////            throw new ResourceNotFoundException("Publicacion con el ID : " + publicacionId + " no encontrada");
//            throw new RuntimeException("User does not exist");
//        }
//        return reservationService.findById(reservation_id).map(reservation -> {
////            reservation.setUser(reservationRequest.getUser());
//            reservation.setDate(reservationRequest.getDate() != null ? reservationRequest.getDate() : reservation.getDate());
//            reservation.setPaid(reservationRequest.getPaid() != null ? reservationRequest.getPaid() : reservation.getPaid());
//            reservation.setCourt(reservationRequest.getCourt() != null ? reservationRequest.getCourt() : reservation.getCourt());
//            reservation.setTime_interval(reservationRequest.getTime_interval() != null ? reservationRequest.getTime_interval() : reservation.getTime_interval());
//            return new ResponseEntity<>(reservationService.save(reservation), HttpStatus.OK);
////        }).orElseThrow(() -> new ResourceNotFoundException("Comentario con el ID : " + comentarioId + " no encontrado"));
//        }).orElseThrow(() -> new RuntimeException("Reservation does not exist"));
//    }
}
