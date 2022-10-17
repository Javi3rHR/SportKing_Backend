package project.Reservations.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.Users.entities.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_game")
@Getter @Setter @ToString
public class UserGame {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long user_game_id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "sport_id")
    private Sport sport;

    @Column
    private String annotation;

    @Column
    private String result;

    @Column
    private String opponents;

}
