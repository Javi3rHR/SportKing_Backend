package project.Users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.Reservations.entities.PlayerLevel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter @Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long user_id;

    @Column
    private String username;

    @Column
    private String name;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String credit_card;

    @Column
    private String image;

    @Enumerated(EnumType.STRING)
    private PlayerLevel playerLevel;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;
}
