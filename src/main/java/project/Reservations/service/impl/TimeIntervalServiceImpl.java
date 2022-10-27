package project.Reservations.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.Reservations.dto.timeInterval.TimeIntervalDto;
import project.Reservations.entities.Court;
import project.Reservations.entities.TimeInterval;
import project.Reservations.exception.ResourceNotFoundException;
import project.Reservations.repository.CourtRepository;
import project.Reservations.repository.TimeIntervalRepository;
import project.Reservations.service.TimeIntervalService;

@Service("timeIntervalService")
public class TimeIntervalServiceImpl implements TimeIntervalService {

    private final TimeIntervalRepository timeIntervalRepository;
    private final CourtRepository courtRepository;

    private final ModelMapper modelMapper;

    public TimeIntervalServiceImpl(TimeIntervalRepository timeIntervalRepository, CourtRepository courtRepository, ModelMapper modelMapper) {
        this.timeIntervalRepository = timeIntervalRepository;
        this.courtRepository = courtRepository;
        this.modelMapper = modelMapper;
    }


    /* #################### GET #################### */
    @Override
    public TimeIntervalDto findById(Long time_interval_id) {
        return null;
    }

//    @Override
//    public List<TimeInterval> findAvailableTimeInterval(Long court_id, String reservation_date, String start_time) {
//        return null;
//    }


    /* #################### POST #################### */

    @Override
    public TimeIntervalDto save(Long court_id, TimeIntervalDto timeIntervalDto) {
        Court court = courtRepository.findById(court_id)
                .orElseThrow(() -> new ResourceNotFoundException("Court", "court_id", court_id));

        if(!timeIntervalIsValid(court_id, timeIntervalDto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time interval is not valid");
        }

        try {
            TimeInterval timeInterval = mapEntity(timeIntervalDto);
            timeInterval.setStart_time(timeIntervalDto.getStart_time());
            timeInterval.setEnd_time(timeIntervalDto.getEnd_time());
            timeInterval.setCourt(court);
            // TODO timeInterval.setEnd_time(calculateEndTime(timeIntervalDto.getStart_time(), timeIntervalDto.getDuration()));
            return mapDTO(timeIntervalRepository.save(timeInterval));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while creating time interval");
        }
    }


//        if (timeIntervalDto.getStart_time().equals(timeIntervalDto.getEnd_time())) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Start time and end time must be different");
//        }
//        if (timeIntervalDto.getStart_time().compareTo(timeIntervalDto.getEnd_time()) > 0) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Start time must be before end time");
//        }

    /* #################### OTHER #################### */

    public Boolean timeIntervalIsValid(Long court_id, TimeIntervalDto timeIntervalDto) {
        if ((timeIntervalRepository.existsByCourtCourtIdAndStart_timeAndEnd_time(
                court_id,
                timeIntervalDto.getStart_time(),
                timeIntervalDto.getEnd_time())) != null) {
            // TODO REVISAR ERROR
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Time interval already exists");
        }
        if (timeIntervalDto.getStart_time().equals(timeIntervalDto.getEnd_time())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Start time and end time must be different");
        }
        if (timeIntervalDto.getStart_time().compareTo(timeIntervalDto.getEnd_time()) > 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Start time must be before end time");
        }
        return true;
    }

    // TODO
    @Override
    public String calculateEndTime(String start_time, int duration) {
        String end_time = "";
        int start_hour = Integer.parseInt(start_time.substring(0, 2));
        int start_minute = Integer.parseInt(start_time.substring(3, 5));
        int end_hour = start_hour + duration / 60;
        int end_minute = start_minute + duration % 60;
        if (end_minute >= 60) {
            end_hour++;
            end_minute -= 60;
        }
        if (end_hour < 10) {
            end_time += "0";
        }
        end_time += end_hour + ":";
        if (end_minute < 10) {
            end_time += "0";
        }
        end_time += end_minute;
        return end_time;
    }

//    @Override
//    public boolean ifHalfHour(String start_time) {
//        return false;
//    }


    /* #################### MAPPER #################### */

    // Convierte entidad a DTO
    private TimeIntervalDto mapDTO(TimeInterval timeInterval) {
        try {
            return modelMapper.map(timeInterval, TimeIntervalDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TimeInterval not mapped.");
        }
    }

    // TODO
//    private TimeIntervalResponseDto mapDTOResponse(TimeInterval timeInterval) {
//        try {
//            return modelMapper.map(timeInterval, TimeIntervalResponseDto.class);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TimeInterval not mapped.");
//        }
//    }

    // Convierte de DTO a Entidad
    private TimeInterval mapEntity(TimeIntervalDto timeIntervalDto) {
        try {
            return modelMapper.map(timeIntervalDto, TimeInterval.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TimeInterval not mapped.");
        }
    }
}

