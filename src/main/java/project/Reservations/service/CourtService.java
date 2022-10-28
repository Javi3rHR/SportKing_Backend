package project.Reservations.service;

import project.Reservations.dto.court.CourtDto;

import java.util.List;

public interface CourtService {
//    CourtDto findById(Long court_id);

    List<CourtDto> findAll();

    /**
     * Busca una pista por su id y el id del deporte al que pertenece
     *
     * @param sport_id
     * @param court_id
     * @return CourtDto
     */
    CourtDto findBySportIdAndCourtId(Long sport_id, Long court_id);

    void delete(Long court_id);

    CourtDto save(CourtDto courtDto, Long sport_id);

    CourtDto update(CourtDto courtDto, Long sport_id);

    boolean checkCourtAlreadyExists(String court_name);

    List<CourtDto> setAvailableTimes(List<CourtDto> CourtRequestDto);
}
