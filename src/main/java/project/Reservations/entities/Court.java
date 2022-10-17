package project.Reservations.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "court")
@Getter @Setter @ToString
public class Court {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long court_id;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Sport sport;

    /* Horario de apertura y de cerrada general Ej. 9:00 - 23:00 */
    @Column
    private String time_interval;

    @Column(name = "court_name")
    private String name;

    @Column(name = "court_price")
    private double price;
}
