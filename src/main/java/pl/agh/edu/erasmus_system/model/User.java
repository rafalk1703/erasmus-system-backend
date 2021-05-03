package pl.agh.edu.erasmus_system.model;

import lombok.*;
import javax.validation.constraints.Email;
import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Email
    @Column(nullable = false)
    private String email;


}
