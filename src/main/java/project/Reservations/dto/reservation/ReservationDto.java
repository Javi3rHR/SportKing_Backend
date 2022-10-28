package project.Reservations.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import project.Reservations.dto.timeInterval.TimeIntervalDto;
import project.Users.dto.UserResponseDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class ReservationDto {

    private long id;

    private long time_interval_id;

    @NotNull(message = "Date is required")
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date date;

    @NotNull(message = "Paid is required")
    private Boolean paid;

    @NotEmpty(message = "User is required")
    private UserResponseDto user;

    @NotEmpty(message = "Time interval is required")
    private TimeIntervalDto time_interval;

    public ReservationDto() {
        super();
    }

}
