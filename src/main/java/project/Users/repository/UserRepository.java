package project.Users.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import project.Users.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    User findByUsername(String username);
    @Query(value = "SELECT * FROM user WHERE user_id = :user_id", nativeQuery = true)
    User findByUserId(Long user_id);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);


}

