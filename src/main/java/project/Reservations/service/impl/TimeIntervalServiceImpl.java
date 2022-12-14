package project.Reservations.service.impl;

import lombok.extern.slf4j.Slf4j;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("timeIntervalService")
public class TimeIntervalServiceImpl implements TimeIntervalService {

    private final TimeIntervalRepository timeIntervalRepository;
    private final CourtRepository courtRepository;
    private final ModelMapper modelMapper;

    /* Inyección de dependencias */
    public TimeIntervalServiceImpl(TimeIntervalRepository timeIntervalRepository, CourtRepository courtRepository, ModelMapper modelMapper) {
        this.timeIntervalRepository = timeIntervalRepository;
        this.courtRepository = courtRepository;
        this.modelMapper = modelMapper;
    }


    /* #################### GET #################### */

    /* Buscar todos los intervalos de tiempo */


    /* Buscar intervalo de tiempo por id */
    @Override
    public TimeIntervalDto findById(Long time_interval_id) {
        return null;
    }

    /* Buscar intervalo de tiempo por court_id y reservation_date*/
    @Override
    public List<TimeIntervalDto> findByCourtCourtIdAndReservationDate(Long court_id, String reservation_date) {
        try{
            // Formato inicial
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(reservation_date);
            // Aplica formato de fecha requerido
            sdf.applyPattern("yyyy-MM-dd");
            String dateFormatted = sdf.format(date);
            date = sdf.parse(dateFormatted);
            List<TimeInterval> timeIntervals = timeIntervalRepository.findAllByCourtCourtIdAndReservationDate(court_id, date);
            return timeIntervals.stream().map(this::mapDTO).collect(Collectors.toList());
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TimeInterval not found");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public List<TimeInterval> findAvailableTimeInterval(Long court_id, String reservation_date, String start_time) {
//        return null;
//    }


    /* #################### POST #################### */

    /* Crear intervalo de tiempo */
    @Override
    public TimeIntervalDto save(Long court_id, TimeIntervalDto timeIntervalDto) {
        Court court = courtRepository.findById(court_id)
                .orElseThrow(() -> new ResourceNotFoundException("Court", "court_id", court_id));

        if (!timeIntervalIsValid(court_id, timeIntervalDto)) {
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

    /* #################### PUT #################### */


    /* #################### DELETE #################### */

    /* Borrar intervalo de tiempo */
    @Override
    public void delete(Long time_interval_id) {
        try {
            timeIntervalRepository.deleteById(time_interval_id);
            log.info("Time interval with id '" + time_interval_id + "' deleted");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while deleting time interval");
        }
    }

    /* #################### VALIDATION #################### */

    /**
     * Comprueba si el intervalo de tiempo es válido
     *
     * @param court_id id de la pista
     * @param timeIntervalDto intervalo de tiempo
     * @return boolean
     */
    public Boolean timeIntervalIsValid(Long court_id, TimeIntervalDto timeIntervalDto) {
        if ((timeIntervalRepository.existsByCourtCourtIdAndStart_timeAndEnd_time(
                court_id,
                timeIntervalDto.getStart_time(),
                timeIntervalDto.getEnd_time())) != null) {
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

    // TODO Calcular end_time a partir de start_time y duration
//    @Override
//    public String calculateEndTime(String start_time, int duration) {
//        String end_time = "";
//        int start_hour = Integer.parseInt(start_time.substring(0, 2));
//        int start_minute = Integer.parseInt(start_time.substring(3, 5));
//        int end_hour = start_hour + duration / 60;
//        int end_minute = start_minute + duration % 60;
//        if (end_minute >= 60) {
//            end_hour++;
//            end_minute -= 60;
//        }
//        if (end_hour < 10) {
//            end_time += "0";
//        }
//        end_time += end_hour + ":";
//        if (end_minute < 10) {
//            end_time += "0";
//        }
//        end_time += end_minute;
//        return end_time;
//    }

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

