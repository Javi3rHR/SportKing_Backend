package project.Reservations.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import project.Reservations.entities.Sport;

public interface SportRepository extends CrudRepository<Sport, Long> {

    @Query(value = "SELECT * FROM sport WHERE sport_name = :sport_name", nativeQuery = true)
    Sport findBySportName(String sport_name);
}
