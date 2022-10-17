package project.Reservations.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "sport")
@Getter @Setter @ToString
public class Sport {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long sport_id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String photo;
}
