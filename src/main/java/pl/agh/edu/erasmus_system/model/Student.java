package pl.agh.edu.erasmus_system.model;

import lombok.*;
import pl.agh.edu.erasmus_system.Utils.Degree;
import pl.agh.edu.erasmus_system.Utils.Year;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "students")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Degree degree;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Year year;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String field;
}
