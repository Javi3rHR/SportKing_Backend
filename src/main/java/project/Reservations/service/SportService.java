package project.Reservations.service;

import project.Reservations.dto.sport.SportDto;

import java.util.List;

public interface SportService {
    List<SportDto> findAll();
    SportDto findByName(String sport_name);
    SportDto findById(Long id);
    SportDto save(SportDto sportDto);
    SportDto update(SportDto sportDto);
    void delete(Long sport_id);
}
