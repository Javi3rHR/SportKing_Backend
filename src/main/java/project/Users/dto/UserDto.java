package project.Users.dto;

import lombok.Getter;
import lombok.Setter;
import project.Users.entities.User;

@Getter @Setter
public class UserDto {

    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;

    public User getUserFromDto(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setName(name);

        return user;
    }
}
