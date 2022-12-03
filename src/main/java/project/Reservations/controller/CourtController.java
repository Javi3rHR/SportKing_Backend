package project.Reservations.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.Reservations.dto.court.CourtDto;
import project.Reservations.service.CourtService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/api")
public class CourtController {

    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }


    /* #################### GET #################### */

    @GetMapping("/courts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CourtDto>> getAll() {
        return new ResponseEntity<>(courtService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/sport/{sport_id}/court/{court_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourtDto> getBySportIdAndCourtId(@PathVariable(value = "sport_id") Long sport_id, @PathVariable(value = "court_id") Long court_id) {
        return new ResponseEntity<>(courtService.findBySportIdAndCourtId(sport_id, court_id), HttpStatus.OK);
    }


    /* #################### POST #################### */

    @PostMapping("sport/{id}/courts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourtDto> save(@PathVariable(value = "id") Long sport_id, @Valid @RequestBody CourtDto courtDto) {
        return new ResponseEntity<>(courtService.save(courtDto, sport_id), HttpStatus.CREATED);
    }


    /* #################### PUT #################### */

    @PutMapping("sport/{id}/courts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourtDto> update(@PathVariable(value = "id") Long sport_id, @Valid @RequestBody CourtDto courtDto) {
        return new ResponseEntity<>(courtService.update(courtDto, sport_id), HttpStatus.CREATED);
    }


    /* #################### DELETE #################### */

    @DeleteMapping("/courts/{court_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable(value = "court_id") Long court_id) {
        courtService.delete(court_id);
        return new ResponseEntity<>("Court has been deleted", HttpStatus.OK);
    }

}
