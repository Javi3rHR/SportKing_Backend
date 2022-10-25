package project.Reservations.service.impl;

import org.modelmapper.ModelMapper;
import project.Reservations.dto.court.CourtDto;
import project.Reservations.entities.Court;
import project.Reservations.repository.CourtRepository;
import project.Reservations.service.CourtService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;

    private final ModelMapper modelMapper;

    public CourtServiceImpl(CourtRepository courtRepository, ModelMapper modelMapper) {
        this.courtRepository = courtRepository;
        this.modelMapper = modelMapper;
    }


    /* #################### GET #################### */

    @Override
    public List<CourtDto> findAll() {
        try{
            List<Court> courts = new ArrayList<>();
            courtRepository.findAll().iterator().forEachRemaining(courts::add);
            return courts.stream().map(this::mapDTO).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("Error while getting all courts");
        }
    }

    @Override
    public CourtDto findById(Long court_id) {
        try{
            Court court = courtRepository.findById(court_id).orElseThrow(() -> new RuntimeException("Court not found"));
            return mapDTO(court);
        }catch (Exception e){
            throw new RuntimeException("Error while getting court by id");
        }
    }


    /* #################### POST #################### */

    @Override
    public CourtDto save(CourtDto courtDto) {
        try {
            if (checkCourtAlreadyExists(courtDto.getName())) {
                throw new RuntimeException("Court already exists");
            }
            Court court = mapEntity(courtDto);
            courtRepository.save(court);
            return mapDTO(court);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving court");
        }
    }


    /* #################### PUT #################### */

    @Override
    public CourtDto update(Long court_id, CourtDto courtDto) {
        if(!courtRepository.existsById(court_id)){
            throw new RuntimeException("Court with id " + court_id + " doesn't exist");
        }
        if (checkCourtAlreadyExists(courtDto.getName())) {
            throw new RuntimeException("Court with name " + courtDto.getName() + " already exists");
        }
        return courtRepository.findById(court_id)
                .map(court -> {
                    court.setName(courtDto.getName() != null ? courtDto.getName() : court.getName());
                    court.setSport(courtDto.getSport() != null ? courtDto.getSport() : court.getSport());
                    court.setPrice(courtDto.getPrice() != 0 ? courtDto.getPrice() : court.getPrice());
                    court.setTime_intervals(courtDto.getTime_intervals() != null ? courtDto.getTime_intervals() : court.getTime_intervals());
                    courtRepository.save(court);
                     return mapDTO(court);
                 }).orElseThrow(() -> new RuntimeException("Court not found"));
    }


    /* #################### DELETE #################### */

    @Override
    public void delete(Long court_id) {
        try{
            Court court = courtRepository.findById(court_id).orElseThrow(() -> new RuntimeException("Court not found"));
            courtRepository.delete(court);
        }catch (Exception e){
            throw new RuntimeException("Error while deleting court");
        }
    }


    /* #################### CHECK #################### */

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
            throw new RuntimeException("Court not mapped.");
        }
    }
    // Convierte de DTO a Entidad
    private Court mapEntity(CourtDto sportDto) {
        try {
            return modelMapper.map(sportDto, Court.class);
        } catch (Exception e) {
            throw new RuntimeException("Court not mapped.");
        }
    }
}
