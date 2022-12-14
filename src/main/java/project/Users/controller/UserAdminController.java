package project.Users.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.Users.dto.UserDto;
import project.Users.entities.User;
import project.Users.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserAdminController {

    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }


    /* #################### GET #################### */

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAll(){
        if(userService.findAll().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or #username == principal.username")
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable("username") String username){
        if(userService.findByUsername(username) == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or @authenticatedUserService.hasId(#user_id)")
    @GetMapping("/{user_id}")
    public ResponseEntity<User> getById(@PathVariable("user_id") Long user_id){
        log.info("User id: " + user_id);
        if(userService.findByUserId(user_id) == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userService.findByUserId(user_id), HttpStatus.OK);
    }


    /* #################### PUT #################### */

    @PreAuthorize("hasRole('ADMIN') or @authenticatedUserService.hasId(#user_id)")
    @PutMapping("/{user_id}")
    public ResponseEntity<User> update(@PathVariable(value = "user_id") Long user_id, @Valid @RequestBody UserDto user){
        if(userService.update(user_id, user) == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userService.update(user_id, user), HttpStatus.OK);
    }


    /* #################### DELETE #################### */

    @PreAuthorize("hasRole('ADMIN') or @authenticatedUserService.hasId(#user_id)")
    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> delete(@PathVariable("user_id") Long user_id){
        userService.delete(user_id);
        log.info("User with id: " + user_id + " has been deleted");
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    // Permite borrar su cuenta al usuario logueado
    @PreAuthorize("hasRole('ADMIN') or #username == principal.username")
    @DeleteMapping("username/{username}")
    public ResponseEntity<String> delete(@PathVariable("username") String username){
        userService.deleteByUsername(username);
        log.info("User " + username + " has been deleted");
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

}