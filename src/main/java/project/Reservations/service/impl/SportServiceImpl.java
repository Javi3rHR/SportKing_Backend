package project.Reservations.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
            throw new RuntimeException("Error while getting all sports");
        }
    }

    @Override
    public SportDto findByName(String name) {
        try{
        Sport sport = sportRepository.findByName(name);
            return mapDTO(sport);
        }catch (Exception e){
            throw new RuntimeException("Error while getting sport by name");
        }
    }

    /* #################### POST #################### */

    @Override
    public SportDto save(SportDto sportDto) {
        try{
        Sport sport = mapEntity(sportDto);
        sportRepository.save(sport);
        return mapDTO(sport);
        }catch (Exception e){
            throw new RuntimeException("Error while saving sport");
        }
    }
    /* #################### PUT #################### */

    @Override
    public SportDto update(SportDto sportDto) {
        return sportRepository.findById(sportDto.getId())
                .map(sport -> {
                    sport.setName(sportDto.getName() != null ? sportDto.getName() : sport.getName());
                    sport.setDescription(sportDto.getDescription() != null ? sportDto.getDescription() : sport.getDescription());
                    sport.setPhoto(sportDto.getPhoto() != null ? sportDto.getPhoto() : sport.getPhoto());
                    sportRepository.save(sport);
                    return mapDTO(sport);
                }).orElseThrow(() -> new RuntimeException("Error while updating sport"));
    }
    /* #################### DELETE #################### */

    @Override
    public void delete(String name) {
        try{
        Sport sport = sportRepository.findByName(name);
        sportRepository.delete(sport);
        }catch (Exception e){
            throw new RuntimeException("Error while deleting sport");
        }
    }

    /* #################### MAPPER #################### */

    // Convierte entidad a DTO
    private SportDto mapDTO(Sport sport) {
        try {
            return modelMapper.map(sport, SportDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Sport not mapped.");
        }
    }
    // Convierte de DTO a Entidad
    private Sport mapEntity(SportDto sportDto) {
        try {
            return modelMapper.map(sportDto, Sport.class);
        } catch (Exception e) {
            throw new RuntimeException("Sport not mapped.");
        }
    }
}

