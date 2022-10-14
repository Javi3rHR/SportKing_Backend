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

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findOne(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(UserDto user) {

        User nUser = user.getUserFromDto();

        if(userRepository.existsByEmail(nUser.getEmail()))
            throw new EmailAlreadyExistsException("Email ocupado");

        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if(nUser.getEmail().split("@")[1].equals("admin.edu")){
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }

        nUser.setRoles(roleSet);
        return userRepository.save(nUser);
    }
}