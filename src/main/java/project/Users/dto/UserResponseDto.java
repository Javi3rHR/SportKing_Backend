package project.Users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseDto {

    private String username;
    private String name;
    private String email;
    private String phone;

    public UserResponseDto() {
        super();
    }
}
