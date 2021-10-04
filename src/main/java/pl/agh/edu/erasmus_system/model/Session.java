package pl.agh.edu.erasmus_system.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;

@Entity(name = "Sessions")
@Table(name = "sessions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contracts_coordinator_id", nullable = false)
    private ContractsCoordinator contractsCoordinator;

    @Column(nullable = false)
    private Date expiryDate;

    @Column(nullable = false)
    private String code;

    public Session(ContractsCoordinator coordinator, Date expiryDate, String sessionCode) {
        this.contractsCoordinator = coordinator;
        this.expiryDate = expiryDate;
        this.code = sessionCode;
    }

    public void extendSessionByOneDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        this.expiryDate = new Date(calendar.getTime().getTime());
    }
}