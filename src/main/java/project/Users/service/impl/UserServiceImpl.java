package project.Users.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.Users.dto.UserDto;
import project.Users.entities.Role;
import project.Users.entities.User;
import project.Users.exception.EmailAlreadyExistsException;
import project.Users.exception.UsernameAlreadyExistsException;
import project.Users.repository.UserRepository;
import project.Users.service.RoleService;
import project.Users.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    /* ########## AUTH ########## */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    /* Encargado de cargar los roles. Crea un conjunto de authorities/roles */
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        ; // Se pone "ROLE_" para que Spring lo detecte bien
        return authorities;
    }

    /* Comprueba que Username y Email están libres y crea la cuenta */
    @Override
    public User save(UserDto user) {

        User nUser = user.getUserFromDto();

        if (userRepository.existsByEmail(nUser.getEmail()))
            throw new EmailAlreadyExistsException("Email ocupado");

        if (userRepository.existsByUsername(nUser.getUsername()))
            throw new UsernameAlreadyExistsException("Username ocupado");

        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        // Asigna el rol de usuario
        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if (nUser.getEmail().split("@")[1].equals("admin.edu")) {
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }

        nUser.setRoles(roleSet);
        return userRepository.save(nUser);
    }


    /* ########## CRUD ########## */

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUserId(Long user_id) {
        return userRepository.findByUserId(user_id);
    }


    @Override
    public User update(Long user_id, UserDto user) {
        User nUser = userRepository.findByUserId(user_id);
        if (nUser != null) {
            nUser.setUsername(user.getUsername());
            nUser.setEmail(user.getEmail());
            nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            return userRepository.save(nUser);
        } else {
            throw new UsernameNotFoundException("User does not exist");
        }
    }

    @Override
    public User delete(Long user_id) {
        User user = userRepository.findByUserId(user_id);
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new UsernameNotFoundException("User does not exist");
        }
        return user;
    }


}
