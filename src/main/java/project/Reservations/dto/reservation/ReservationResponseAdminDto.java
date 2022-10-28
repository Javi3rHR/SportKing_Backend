package project.Reservations.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import project.Reservations.dto.timeInterval.TimeIntervalDto;
import project.Users.dto.UserResponseDto;

import java.util.Date;

@Getter
@Setter
public class ReservationResponseAdminDto {
    private long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
    private Boolean paid;
    private UserResponseDto user;
    private TimeIntervalDto time_interval;

    public ReservationResponseAdminDto() {
        super();
    }

}
