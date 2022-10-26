package project.Reservations.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.Reservations.dto.sport.SportDto;
import project.Reservations.entities.Sport;
import project.Reservations.repository.SportRepository;
import project.Reservations.service.SportService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("sportService")
public class SportServiceImpl implements SportService {

    private final SportRepository sportRepository;

    private final ModelMapper modelMapper;

    public SportServiceImpl(SportRepository sportRepository, ModelMapper modelMapper) {
        this.sportRepository = sportRepository;
        this.modelMapper = modelMapper;
    }

    /* #################### GET #################### */

    @Override
    public List<SportDto> findAll() {
        try{
        List<Sport> sports = new ArrayList<>();
        sportRepository.findAll().iterator().forEachRemaining(sports::add);
        return sports.stream().map(this::mapDTO).collect(Collectors.toList());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while getting all sports");
        }
    }

    @Override
    public SportDto findByName(String sport_name) {
        try{
        Sport sport = sportRepository.findBySportName(sport_name);
            return mapDTO(sport);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while getting sport by name");
        }
    }

    @Override
    public SportDto findById(Long id) {
        try{
        Sport sport = sportRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sport not found"));
            return mapDTO(sport);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while getting sport by id");
        }
    }

    /* #################### POST #################### */

    @Override
    public SportDto save(SportDto sportDto) {
        if (sportRepository.findBySportName(sportDto.getSport_name()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sport with name '" + sportDto.getSport_name() + "' already exists");
        }
        try{
        Sport sport = mapEntity(sportDto);
        sportRepository.save(sport);
        return mapDTO(sport);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while saving sport");
        }
    }
    /* #################### PUT #################### */

    @Override
    public SportDto update(SportDto sportDto) {
        if (!sportRepository.existsById(sportDto.getId())) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Sport with id '" + sportDto.getId() + "' doesn't exist");
        }
        if (sportRepository.findBySportName(sportDto.getSport_name()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sport with name '" + sportDto.getSport_name() + "' already exists");
        }
        return sportRepository.findById(sportDto.getId())
                .map(sport -> {
                    sport.setSport_name(sportDto.getSport_name() != null ? sportDto.getSport_name() : sport.getSport_name());
                    sport.setDescription(sportDto.getDescription() != null ? sportDto.getDescription() : sport.getDescription());
                    sport.setPhoto(sportDto.getPhoto() != null ? sportDto.getPhoto() : sport.getPhoto());
                    sportRepository.save(sport);
                    return mapDTO(sport);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while updating sport"));
    }
    /* #################### DELETE #################### */

    @Override
    public void delete(Long sport_id) {
        try{
        Sport sport = sportRepository.findById(sport_id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sport with id '" + sport_id + "' not found"
                ));
        sportRepository.delete(sport);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while deleting sport");
        }
    }

    /* #################### MAPPER #################### */

    // Convierte entidad a DTO
    private SportDto mapDTO(Sport sport) {
        try {
            return modelMapper.map(sport, SportDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sport not mapped.");
        }
    }
    // Convierte de DTO a Entidad
    private Sport mapEntity(SportDto sportDto) {
        try {
            return modelMapper.map(sportDto, Sport.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sport not mapped.");
        }
    }
}

