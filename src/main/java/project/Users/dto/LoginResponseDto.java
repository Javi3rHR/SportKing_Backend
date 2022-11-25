package project.Users.dto;

import lombok.Getter;
import lombok.Setter;
import project.Security.jwt.AuthTokenDto;
import project.Users.entities.User;

@Getter @Setter
public class LoginResponseDto {
    private AuthTokenDto token;
    private User user;

    public LoginResponseDto() {
        super();
    }

    public LoginResponseDto(AuthTokenDto authTokenDto, User user) {
        this.token = authTokenDto;
        this.user = user;
    }
}
