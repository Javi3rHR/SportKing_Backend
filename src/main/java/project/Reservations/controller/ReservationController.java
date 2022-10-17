package project.Reservations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.Reservations.entities.Reservation;
import project.Reservations.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reservation>> getAll(){
        return new ResponseEntity<>(reservationService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Reservation>> getAllByUserId(@PathVariable Long user_id){
        return new ResponseEntity<>(reservationService.findAllByUserId(user_id), HttpStatus.OK);
    }

}
