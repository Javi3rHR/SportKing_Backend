package project.Reservations.service;

import project.Reservations.dto.sport.SportDto;

import java.util.List;

public interface SportsService {
    List<SportDto> findAll();
}
