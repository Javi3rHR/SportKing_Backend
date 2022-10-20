package project.Reservations.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReservationResponse {
    private long id;
    private long user_id;
    private String username;
    private String email;
    private String phone;
    private String court;
    private String date;
    private String time_interval;
    private Boolean paid;
//    @JsonIgnore
//    private User user;
//    public User userResponse() {
//        userResponse().setUser_id(user.getUser_id());
//        userResponse().setUsername(user.getUsername());
//        userResponse().setEmail(user.getEmail());
//        userResponse().setPhone(user.getPhone());
//        return userResponse();
//    }
    public ReservationResponse() {
        super();
    }


}
