package project.Users.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import project.Users.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByEmail(String email);
}

