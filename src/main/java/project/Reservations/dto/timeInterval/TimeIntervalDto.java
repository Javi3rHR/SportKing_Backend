package project.Reservations.dto.timeInterval;

import lombok.Getter;
import lombok.Setter;
import project.Reservations.entities.Court;

@Getter @Setter
public class TimeIntervalDto {

        private long id;
        private String start_time;
        private int duration;
        private Court court;

        public TimeIntervalDto() {
            super();
        }
}
