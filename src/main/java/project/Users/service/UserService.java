package project.Users.service;

import project.Users.dto.UserDto;
import project.Users.entities.User;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    User findOne(String username);
}
