package project.Users.service;

import project.Users.dto.UserDto;
import project.Users.entities.User;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    User findOne(String username);
    User delete(Long user_id);
    User update(Long user_id, UserDto user);
}
