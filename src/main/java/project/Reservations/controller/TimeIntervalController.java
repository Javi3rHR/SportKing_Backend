package project.Reservations.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.Reservations.dto.timeInterval.TimeIntervalDto;
import project.Reservations.service.TimeIntervalService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TimeIntervalController {

    private final TimeIntervalService timeIntervalService;

    public TimeIntervalController(TimeIntervalService timeIntervalService) {
        this.timeIntervalService = timeIntervalService;
    }

    /* #################### GET #################### */

    @GetMapping(value = "/courts/{court_id}/time_intervals", params = "reservation_date")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TimeIntervalDto>> findAllByCourtCourtIdAndReservationDate(@PathVariable Long court_id, @RequestParam String reservation_date) {
        return new ResponseEntity<>(timeIntervalService.findByCourtCourtIdAndReservationDate(court_id, reservation_date), HttpStatus.OK);
    }


    /* #################### POST #################### */

    @PostMapping(value = "/courts/{court_id}/time_intervals")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TimeIntervalDto> createTimeInterval(@PathVariable("court_id") Long court_id, @Valid @RequestBody TimeIntervalDto timeIntervalDto) {
        return new ResponseEntity<>(timeIntervalService.save(court_id, timeIntervalDto), HttpStatus.CREATED);
    }


    /* #################### PUT #################### */




    /* #################### DELETE #################### */

    @DeleteMapping("time_intervals/{time_interval_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTimeInterval(@PathVariable("time_interval_id") Long time_interval_id) {
        timeIntervalService.delete(time_interval_id);
        return new ResponseEntity<>("Time interval with id '" + time_interval_id + "' has been deleted", HttpStatus.OK);
    }

}
