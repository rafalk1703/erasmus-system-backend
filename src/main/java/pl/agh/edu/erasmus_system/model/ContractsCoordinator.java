package pl.agh.edu.erasmus_system.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "contractCoordinators")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ContractsCoordinator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column
    private String email;

    @Column
    private String hash;

    @Column(nullable = false)
    private String code;

    @Column
    @Enumerated(EnumType.STRING)
    private CoordinatorRole role;
}
