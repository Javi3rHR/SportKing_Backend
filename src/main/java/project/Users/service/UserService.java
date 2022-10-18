package project.Users.service;

import project.Users.dto.UserDto;
import project.Users.entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByUsername(String username);
    User findByUserId(Long user_id);
    User save(UserDto user);
    User delete(Long user_id);
    User update(Long user_id, UserDto user);
}
