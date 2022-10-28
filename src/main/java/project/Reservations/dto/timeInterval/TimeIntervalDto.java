package project.Reservations.dto.timeInterval;

import lombok.Getter;
import lombok.Setter;
import project.Reservations.dto.court.CourtDto;

@Getter @Setter
public class TimeIntervalDto {

        private long id;
        private String start_time;
        private String end_time;
        private CourtDto court;

        public TimeIntervalDto() {
            super();
        }
}
