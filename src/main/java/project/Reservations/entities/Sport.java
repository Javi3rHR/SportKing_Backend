package project.Reservations.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sport")
@Getter @Setter @ToString
public class Sport {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "sport_id")
    private long id;

    @Column(name = "sport_name", length = 50)
    @Size(max = 50, message = "Max length is 50")
    private String sport_name;

    @Column(name = "sport_description", length = 244)
    @Size(max = 244, message = "Max length is 244")
    private String description;

    @Column(name = "sport_photo")
    private String photo;
}
