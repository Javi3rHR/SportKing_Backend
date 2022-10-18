package project.Reservations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.Reservations.entities.Reservation;
import project.Reservations.service.ReservationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


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

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> save( @Valid @RequestBody Reservation reservation){
        if (reservationService.save(reservation) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(reservationService.save(reservation), HttpStatus.CREATED);
    }

}
