package project.Reservations.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.Users.entities.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_game")
@Getter @Setter @ToString
public class UserGame {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_game_id")
    private long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "sport_id")
    @Column(length = 50)
    private Sport sport;

    @Column
    @Size(max = 244, message = "Max length is 244")
    private String annotation;

    @Column
    @Size(max = 244, message = "Max length is 244")
    private String result;

    @Column
    @Size(max = 244, message = "Max length is 244")
    private String opponents;

}
