package project.Reservations.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import project.Reservations.entities.Court;

import java.util.Date;

@Getter @Setter
public class ReservationResponseDto {
    private long id;
    private long user_id;
    private String username;
    private String email;
    private String phone;
    private Court court;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date date;
    private String time_interval;
    private Boolean paid;

    public ReservationResponseDto() {
        super();
    }

}
