package pl.agh.edu.erasmus_system.model;

import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contracts_coordinator_id", nullable = false)
    private ContractsCoordinator contractsCoordinator;

    @Column(nullable = false)
    private String erasmusCode;

    @JoinColumn(nullable = false)
    private String academy;

    @Column(nullable = false)
    private Integer vacancies;

    @Column(nullable = false)
    private String degree;

    @Column(nullable = false)
    private String startYear;

    @Column(nullable = false)
    private String endYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edition_id", nullable = false)
    private Edition edition;

    @Column(nullable = false)
    private String faculty;
}
