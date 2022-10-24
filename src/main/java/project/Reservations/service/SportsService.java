package project.Reservations.service;

import project.Reservations.dto.sport.SportResponseDto;

import java.util.List;

public interface SportsService {
    List<SportResponseDto> findAll();
}
