package project.Reservations.dto.sport;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class SportDto {

        private long id;
        @NotNull(message = "Name is required")
        private String sport_name;
        private String description;
        private String photo;

        public SportDto() {
            super();
        }
}
