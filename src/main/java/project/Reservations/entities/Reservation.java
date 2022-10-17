package project.Reservations.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.Users.entities.User;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
@Getter @Setter @ToString
public class Reservation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long reservation_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "court_id")
    private Court court;

    @OneToOne
    @JoinColumn(name = "time_interval_id")
    private TimeInterval time_interval;

    @Column
    private String date;

    @Column
    private Boolean paid;
}
