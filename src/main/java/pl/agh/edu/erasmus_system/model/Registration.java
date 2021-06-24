package pl.agh.edu.erasmus_system.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "registrations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private Integer priority;

    @Column(nullable = false)
    private Boolean isNominated;

    @Column(nullable = false)
    private Boolean isAccepted;

}
