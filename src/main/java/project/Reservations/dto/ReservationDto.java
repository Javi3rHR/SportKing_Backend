package project.Reservations.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import project.Reservations.entities.Court;
import project.Reservations.entities.TimeInterval;
import project.Users.entities.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReservationDto {

    private long id;

    @NotEmpty(message = "User is required")
    private User user;

    @NotEmpty(message = "Court is required")
    private Court court;
    private TimeInterval time_interval;

    @NotNull(message = "Date is required")
    @JsonFormat(pattern="YYYY-MM-dd")
    private String date;

    @NotNull(message = "Paid is required")
    private Boolean paid;

    public ReservationDto() {
        super();
    }

}
