package project.Reservations.repository;

import org.springframework.data.repository.CrudRepository;
import project.Reservations.entities.Sport;

public interface SportRepository extends CrudRepository<Sport, Long> {
    Sport findByName(String name);
}
