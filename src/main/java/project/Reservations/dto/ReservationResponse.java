package project.Reservations.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReservationResponse {
    private long id;
    private String user_id;
    private String username;
    private String court;
    private String date;
    private String time_interval;
    private Boolean paid;

    public ReservationResponse() {
        super();
    }


}
