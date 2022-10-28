package project.Reservations.dto.court;

import lombok.Getter;
import lombok.Setter;
import project.Reservations.entities.Sport;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CourtDto {

        private long id;
        @NotNull(message = "Name is required")
        private String name;
        private double price;
        @NotEmpty(message = "Sport is required")
        private Sport sport;

        public CourtDto() {
            super();
        }
}
