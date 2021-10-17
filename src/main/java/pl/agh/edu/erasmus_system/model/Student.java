package pl.agh.edu.erasmus_system.model;

import lombok.*;

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
    private String year;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String field;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String periodOfStay;

    @Column(nullable = false)
    private String earlierParticipation;

    private String averageGrade;

    private String examLevel;

    private String typeOfCertificate;

    private String foreignLanguage;

    private String Address;
}
