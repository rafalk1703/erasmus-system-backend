package pl.agh.edu.erasmus_system.controller.qualification.response_bodies;

import java.util.*;

/**
 * POJO class contains body of response /qualificationView
 */
public class QualificationResponseBody {
    private List<QualificationSingleResponseBody> contracts;
    private Map<Long,QualificationStudentRegistrationsResponseBody> studentsRegistrations;
    private Long conflictsAmount;

    public QualificationResponseBody() {
        this.contracts = new ArrayList<>();
        this.studentsRegistrations = new HashMap<>();
        this.conflictsAmount = 0L;
    }

    public void addContract(QualificationSingleResponseBody contract) {
        this.contracts.add(contract);
    }

    public void setStudentsRegistrations(Map<Long,QualificationStudentRegistrationsResponseBody> studentsRegistrations) {
        this.studentsRegistrations = studentsRegistrations;
    }

    public void setConflictsAmount(Long conflictsAmount) {
        this.conflictsAmount = conflictsAmount;
    }

    public List<QualificationSingleResponseBody> getContracts() {
        return contracts;
    }

    public Map<Long,QualificationStudentRegistrationsResponseBody> getStudentsRegistrations() {
        return studentsRegistrations;
    }

    public Long getConflictsAmount() {
        return conflictsAmount;
    }
}