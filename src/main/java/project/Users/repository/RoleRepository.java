package project.Users.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.Users.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}