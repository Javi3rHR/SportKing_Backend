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

    @Column
    private String name;

    @Column
    private double price;
}
