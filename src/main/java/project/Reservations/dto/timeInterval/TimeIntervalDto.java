package project.Reservations.dto.timeInterval;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TimeIntervalDto {

        private long id;
        private long court_id;
        private String start_time;
        private String end_time;

        public TimeIntervalDto() {
            super();
        }
}
