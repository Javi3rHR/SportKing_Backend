package project.Reservations.dto.court;

import lombok.Getter;
import lombok.Setter;
import project.Reservations.entities.Sport;
import project.Reservations.entities.TimeInterval;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CourtDto {

        private long id;
        @NotNull(message = "Name is required")
        private String name;
        @NotEmpty(message = "Sport is required")
        private Sport sport;
        private double price;
        private List<TimeInterval> time_intervals;

        public CourtDto() {
            super();
        }
}
