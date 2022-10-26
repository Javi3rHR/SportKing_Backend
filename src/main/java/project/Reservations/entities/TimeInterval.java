package project.Reservations.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "time_interval")
@Getter @Setter @ToString
public class TimeInterval {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "time_interval_id")
    private long id;

    /* 9:00 - 10:30 */
    @Column
    @JsonFormat(pattern="HH:mm")
    private String start_time;

    @JsonFormat(pattern="HH:mm")
    @Column
    private String end_time;

    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;

}
