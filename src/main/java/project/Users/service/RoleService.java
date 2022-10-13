package project.Users.service;

import project.Users.entities.Role;

public interface RoleService {
    Role findByName(String name);
}