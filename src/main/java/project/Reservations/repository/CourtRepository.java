package project.Reservations.repository;

import org.springframework.data.repository.CrudRepository;
import project.Reservations.entities.Court;

public interface CourtRepository extends CrudRepository<Court, Long> {

}
