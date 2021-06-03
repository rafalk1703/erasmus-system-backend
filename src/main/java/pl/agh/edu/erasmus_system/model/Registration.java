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

    @ManyToOne
    private Contract contractId;

    @ManyToOne
    private Student studentId;

    @Column(nullable = false)
    private Long priority;

    @Column(nullable = false)
    private Boolean isNominated;

    @Column(nullable = false)
    private Boolean isAccepted;

}
