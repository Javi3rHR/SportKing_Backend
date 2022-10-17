package project.Reservations.entities;

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
    private long time_interval_id;

    @OneToOne
    @JoinColumn(name = "court_id")
    private Court court;

    @Column
    private String start_time;

    @Column
    private String end_time;

}
