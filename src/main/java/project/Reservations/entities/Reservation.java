package project.Reservations.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "court_id", referencedColumnName = "court_id")
    private Court court;

    @OneToOne
    @JoinColumn(name = "time_interval_id", referencedColumnName = "time_interval_id")
    private TimeInterval time_interval;

    @Column(name = "reservation_date")
    @JsonFormat(pattern="YYYY-MM-dd")
    private String date;

    @Column
    private Boolean paid;
}
