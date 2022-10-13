package project.Users.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import project.Users.dto.UserDto;
import project.Users.entities.User;
import project.Users.repository.UserRepository;
import project.Users.service.RoleService;
import project.Users.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(UserDto user) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findOne(String username) {
        return null;
    }
}
