package project.Users.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello-admin")
    public String adminPing(){
        return "Only Admins Can Read This";
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/hello-admin-user")
    public String adminUser(){
        return "Only Admins and Users Can Read This";
    }

//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello-user")
    public String userPing(){
        return "Any User Can Read This";
    }
}
