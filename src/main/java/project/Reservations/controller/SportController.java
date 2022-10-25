package project.Reservations.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.Reservations.dto.sport.SportDto;
import project.Reservations.service.SportService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SportController {

    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    /* #################### GET #################### */

    @GetMapping("/sports")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SportDto>> getAll() {
        return new ResponseEntity<>(sportService.findAll(), HttpStatus.OK);
    }


    /* #################### POST #################### */
    @PostMapping("/sports")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SportDto> save(@Valid @RequestBody SportDto sportDto) {
        return new ResponseEntity<>(sportService.save(sportDto), HttpStatus.CREATED);
    }


    /* #################### PUT #################### */

    @PutMapping("/sports")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SportDto> update(@Valid @RequestBody SportDto sportDto) {
        return new ResponseEntity<>(sportService.update(sportDto), HttpStatus.OK);
    }


    /* #################### DELETE #################### */

    @DeleteMapping("/sports/{sport_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable(value = "sport_id") Long sport_id) {
        sportService.delete(sport_id);
        return new ResponseEntity<>("Sport has been deleted", HttpStatus.OK);
    }

}
