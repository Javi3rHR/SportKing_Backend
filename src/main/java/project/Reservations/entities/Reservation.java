package project.Reservations.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.Users.entities.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter @Setter @ToString
public class Reservation {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @OneToOne
    @JoinColumn(name = "court_id", referencedColumnName = "court_id")
    private Court court;

    @OneToOne
    @JoinColumn(name = "time_interval_id", referencedColumnName = "time_interval_id")
    private TimeInterval time_interval;

    @Column(name = "reservation_date")
    @JsonFormat(pattern="dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private Boolean paid;

}
