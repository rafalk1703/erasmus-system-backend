package pl.agh.edu.erasmus_system.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Contracts")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contracts_coordinator_id", nullable = false)
    private ContractsCoordinator contractsCoordinator;

    @Column(nullable = false)
    private String erasmusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academy_id", nullable = false)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edition_id", nullable = false)
    private Edition edition;
}
