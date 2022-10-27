package project.Reservations.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.Reservations.dto.timeInterval.TimeIntervalDto;
import project.Reservations.service.TimeIntervalService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TimeIntervalController {

    private final TimeIntervalService timeIntervalService;

    public TimeIntervalController(TimeIntervalService timeIntervalService) {
        this.timeIntervalService = timeIntervalService;
    }

    /* #################### GET #################### */

    @PostMapping("/courts/{court_id}/time_intervals")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TimeIntervalDto> createTimeInterval(@PathVariable("court_id") Long court_id, @Valid @RequestBody TimeIntervalDto timeIntervalDto) {
        return new ResponseEntity<>(timeIntervalService.save(court_id, timeIntervalDto), HttpStatus.CREATED);
    }


    /* #################### POST #################### */




    /* #################### PUT #################### */




    /* #################### DELETE #################### */

}
