package project.Reservations.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import project.Reservations.entities.TimeInterval;
import project.Users.entities.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class ReservationDto {

    private long id;

    private long time_interval_id;
    @NotEmpty(message = "User is required")
    private User user;

    @NotEmpty(message = "Time interval is required")
    private TimeInterval time_interval;

    @NotNull(message = "Date is required")
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date date;

    @NotNull(message = "Paid is required")
    private Boolean paid;

    public ReservationDto() {
        super();
    }

}
