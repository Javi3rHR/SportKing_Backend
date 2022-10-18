package project.Users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.Reservations.entities.PlayerLevel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter @Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long user_id;

    @Column(unique = true, length = 50)
    @NotNull @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Column(length = 100)
    @Size(min = 3, max = 100)
    private String name;

    @Column()
    @JsonIgnore
    @NotNull @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    private String password;

    @Column(unique = true, length = 50)
    @NotNull @NotBlank(message = "Email is mandatory")
    @Email(message = "Email must be valid")
    private String email;

    @Column(length = 50)
    @Size(max = 50)
    private String phone;

    @Column(length = 100)
    @Size(max = 100)
    private String credit_card;

    @Column
    private String image;

    @Column(length = 20)
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
