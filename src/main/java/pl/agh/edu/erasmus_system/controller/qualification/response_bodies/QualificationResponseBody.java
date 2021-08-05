package pl.agh.edu.erasmus_system.controller.qualification.response_bodies;

import java.util.*;

/**
 * POJO class contains body of response /qualificationView
 */
public class QualificationResponseBody {
    private List<QualificationSingleResponseBody> contracts;
    private Map<Long,QualificationStudentRegistrationsResponseBody> studentsRegistrations;

    public QualificationResponseBody() {
        this.contracts = new ArrayList<>();
        this.studentsRegistrations = new HashMap<>();
    }

    public void addContract(QualificationSingleResponseBody contract) {
        this.contracts.add(contract);
    }

    public void setStudentsRegistrations(Map<Long,QualificationStudentRegistrationsResponseBody> studentsRegistrations) {
        this.studentsRegistrations = studentsRegistrations;
    }

    public List<QualificationSingleResponseBody> getContracts() {
        return contracts;
    }

    public Map<Long,QualificationStudentRegistrationsResponseBody> getStudentsRegistrations() {
        return studentsRegistrations;
    }
}