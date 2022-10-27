package project.Reservations.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.Reservations.dto.court.CourtDto;
import project.Reservations.entities.Court;
import project.Reservations.repository.CourtRepository;
import project.Reservations.repository.SportRepository;
import project.Reservations.service.CourtService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("courtService")
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;

    private final SportRepository sportRepository;

    private final ModelMapper modelMapper;

    /* Inyección de dependencias */
    public CourtServiceImpl(CourtRepository courtRepository, ModelMapper modelMapper, SportRepository sportRepository) {
        this.courtRepository = courtRepository;
        this.modelMapper = modelMapper;
        this.sportRepository = sportRepository;
    }


    /* #################### GET #################### */

    /* Buscar todas las pistas */
    @Override
    public List<CourtDto> findAll() {
        try {
            List<Court> courts = new ArrayList<>();
            courtRepository.findAll().iterator().forEachRemaining(courts::add);
            return courts.stream().map(this::mapDTO).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while getting all courts");
        }
    }

    /* Buscar pista por id de pista y id de deporte */
    @Override
    public CourtDto findBySportIdAndCourtId(Long sport_id, Long court_id) {
        if (sportRepository.findById(sport_id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Sport does not exist");
        }
        if (courtRepository.findById(court_id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Court does not exist");
        }
        try {
            Court court = courtRepository.findBySportIdAndCourtId(sport_id, court_id);
            return mapDTO(court);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while getting court by sport id and court id");
        }
    }


    /* #################### POST #################### */

    @Override
    public CourtDto save(CourtDto courtDto, Long sport_id) {
        try {
            if (checkCourtAlreadyExists(courtDto.getName())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Court already exists");
            }
            if (sportRepository.findById(sport_id).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Sport does not exist");
            }
            Court court = mapEntity(courtDto);
            court.setSport(sportRepository.findById(sport_id).get());
            return mapDTO(courtRepository.save(court));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while saving court");
        }
    }


    /* #################### PUT #################### */

    @Override
    public CourtDto update(CourtDto courtDto, Long sport_id) {
        if (sportRepository.findById(sport_id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Sport does not exist");
        }
        if (!courtRepository.existsById(courtDto.getId())) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Court with id " + courtDto.getId() + " doesn't exist");
        }
        if (checkCourtAlreadyExists(courtDto.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Court with name " + courtDto.getName() + " already exists");
        }
        if (courtRepository.findById(courtDto.getId()).get().getSport().getId() != sport_id) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Court with id " + courtDto.getId() + " doesn't belong to sport with id " + sport_id);
        }
        return courtRepository.findById(courtDto.getId()).map(court -> {
            court.setName(courtDto.getName() != null ? courtDto.getName() : court.getName());
            court.setPrice(courtDto.getPrice() != 0 ? courtDto.getPrice() : court.getPrice());
            return mapDTO(courtRepository.save(court));
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while updating court"));
    }


    /* #################### DELETE #################### */

    @Override
    public void delete(Long court_id) {
        try {
            Court court = courtRepository.findById(court_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Court not found"));
            courtRepository.delete(court);
            log.info("Court with id '" + court_id + "' deleted");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while deleting court");
        }
    }


    /* #################### CHECK #################### */

    /* Comprobar si existe una pista con el mismo nombre */
    @Override
    public boolean checkCourtAlreadyExists(String court_name) {
        return courtRepository.findByCourtName(court_name) != null;
    }


    /* #################### SETTER #################### */

    @Override
    public List<CourtDto> setAvailableTimes(List<CourtDto> CourtRequestDto) {
        return null;
    }


    /* #################### MAPPER #################### */

    // Convierte entidad a DTO
    private CourtDto mapDTO(Court court) {
        try {
            return modelMapper.map(court, CourtDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Court not mapped.");
        }
    }

    // Convierte de DTO a Entidad
    private Court mapEntity(CourtDto sportDto) {
        try {
            return modelMapper.map(sportDto, Court.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Court not mapped.");
        }
    }
}
