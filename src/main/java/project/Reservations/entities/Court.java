package project.Reservations.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "court")
@Getter @Setter @ToString
public class Court {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "court_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Sport sport;

    @Column(name = "court_name", length = 50)
    @Size(max = 50, message = "Max length is 50")
    private String name;

    @Column(name = "court_price")
    private double price;

}
