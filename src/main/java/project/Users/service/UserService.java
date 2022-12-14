package project.Users.service;

import project.Users.dto.UserDto;
import project.Users.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User findByUsername(String username);
    User findByUserId(Long user_id);
    User save(UserDto user);
    void delete(Long user_id);
    void deleteByUsername(String username);
    User update(Long user_id, UserDto user);

    boolean existsById(Long user_id);
    Optional<User> findById(Long user_id);
}
