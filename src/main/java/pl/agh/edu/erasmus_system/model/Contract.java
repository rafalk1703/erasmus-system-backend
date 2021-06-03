package pl.agh.edu.erasmus_system.model;

import lombok.*;
import pl.agh.edu.erasmus_system.Utils.Degree;

import javax.persistence.*;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ContractsCoordinator contractsCoordinator;

    @Column(nullable = false)
    private String erasmusCode;

    @ManyToOne
    private Academy academy;

    @Column(nullable = false)
    private Long vacancies;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Degree degree;

    @Column(nullable = false)
    private Long startYear;

    @Column(nullable = false)
    private Long endYear;

    @ManyToOne
    private Edition edition;
}
