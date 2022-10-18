package project.Reservations.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reservation>> getAll(){
        if(reservationService.findAll().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservationService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/users/{user_id}/reservations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Reservation>> getAllByUserId(@PathVariable("user_id") Long user_id){
        if(reservationService.findAllByUserId(user_id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservationService.findAllByUserId(user_id), HttpStatus.OK);
    }


    /* ########## POST ########## */
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> save( @Valid @RequestBody Reservation reservation){
        if (reservationService.save(reservation) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(reservationService.save(reservation), HttpStatus.CREATED);
    }

    @PostMapping("/users/{user_id}/reservations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Reservation saveByUserId(@PathVariable("user_id") Long user_id, @Valid @RequestBody Reservation reservation){
        return userService.findById(user_id).map(user -> {
            reservation.setUser(user);
            return reservationService.save(reservation);
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    /* ########## DELETE ########## */

    // https://github.com/ChristianRaulRamirez/ejemplos-relaciones-api-rest/blob/master/api-rest-publicaciones-comentarios/src/main/java/com/api/rest/publicaciones/controladores/ComentarioController.java

//    @DeleteMapping("/users/{user_id}/reservations/{reservation_id}")
//    public ResponseEntity<?> delete(@PathVariable(value = "user_id") Long user_id,@PathVariable(value = "reservation_id") Long reservation_id){
//        return reservationService.findByIdAndUserId(reservation_id, user_id).map(reservation -> {
//            reservationService.delete(reservation);
//            return ResponseEntity.ok().build();
////        }).orElseThrow(() -> new ResourceNotFoundException("Comentario con el ID : " + comentarioId + " no encontrado y publicacion con el ID : " + publicacionId + " no encontrada"));
//        }).orElseThrow(() -> new RuntimeException("Reservation does not exist"));
//    }

    /* ########## PUT ########## */
//    @PutMapping("/users/{user_id}/reservations/{reservation_id}")
//    public Reservation update(@PathVariable(value = "user_id") Long user_id,@PathVariable(value = "reservation_id") Long reservation_id,@Valid @RequestBody Reservation reservationRequest) {
//        if(!userService.existsById(user_id)) {
////            throw new ResourceNotFoundException("Publicacion con el ID : " + publicacionId + " no encontrada");
//            throw new RuntimeException("User does not exist");
//        }
//
//        return reservationService.findById(reservation_id).map(reservation -> {
//            reservation.setTexto(reservationRequest.getTexto());
//            return reservationService.save(reservation);
////        }).orElseThrow(() -> new ResourceNotFoundException("Comentario con el ID : " + comentarioId + " no encontrado"));
//    }).orElseThrow(() -> new RuntimeException("Reservation does not exist"));
}
