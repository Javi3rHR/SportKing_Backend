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

    @Column(name = "sport_name")
    private String name;

    @Column(name = "sport_description")
    private String description;

    @Column(name = "sport_photo")
    private String photo;
}
