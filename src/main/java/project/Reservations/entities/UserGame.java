package project.Reservations.entities;

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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Sport sport;

    @Column
    private String annotation;

    @Column
    private String result;

    @Column
    private String opponents;

}
