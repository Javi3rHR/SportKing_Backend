package project.Reservations.service;

import project.Reservations.dto.court.CourtDto;

import java.util.List;

public interface CourtService {
    CourtDto findById(Long court_id);
    List<CourtDto> findAll();
    void delete(Long court_id);
    CourtDto save(CourtDto courtDto);
    CourtDto update(Long court_id, CourtDto courtDto);
    boolean checkCourtAlreadyExists(String court_name);
    List<CourtDto> setAvailableTimes(List<CourtDto> CourtRequestDto);
}
