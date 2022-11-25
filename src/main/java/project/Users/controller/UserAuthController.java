package project.Users.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.Security.jwt.TokenProvider;
import project.Security.jwt.AuthTokenDto;
import project.Users.dto.LoginResponseDto;
import project.Users.dto.LoginUser;
import project.Users.dto.UserDto;
import project.Users.entities.User;
import project.Users.service.UserService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserAuthController {

    private AuthenticationManager authenticationManager;
    private TokenProvider jwtTokenUtil;
    private UserService userService;

    public UserAuthController(AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    /* Recibe un objeto LoginUser con solo User y Password y retorna token si sale bien. */
    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@Valid @RequestBody LoginUser loginUser) throws AuthenticationException {

        // Se pasa user y password para que Spring se encargue del auth
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        final User user = userService.findByUsername(loginUser.getUsername());
        final AuthTokenDto authTokenDto = new AuthTokenDto(token);
        return ResponseEntity.ok(new LoginResponseDto(authTokenDto, user));
    }

    @PostMapping("/register")
    public User saveUser(@Valid @RequestBody UserDto user){
        return userService.save(user);
    }

}