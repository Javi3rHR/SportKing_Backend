package project.Reservations.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import project.Reservations.entities.Court;

public interface CourtRepository extends CrudRepository<Court, Long> {

    @Query(value = "SELECT * FROM court WHERE court_name = :court_name", nativeQuery = true)

    Court findByCourtName(String court_name);

    @Query(value = "SELECT * FROM court WHERE sport_id = :sport_id AND court_id = :court_id", nativeQuery = true)
    Court findBySportIdAndCourtId(Long sport_id, Long court_id);
}
