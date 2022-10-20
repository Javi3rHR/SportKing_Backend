package project.Reservations.dto.reservation;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReservationResponseDto {
    private long id;
    private long user_id;
    private String username;
    private String email;
    private String phone;
    private String court;
    private String date;
    private String time_interval;
    private Boolean paid;

    public ReservationResponseDto() {
        super();
    }

}
