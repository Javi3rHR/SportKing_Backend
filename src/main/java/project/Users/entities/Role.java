package project.Users.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long role_id;

    @Column(length = 20)
    private String name;

    @Column(length = 50)
    private String description;
}
