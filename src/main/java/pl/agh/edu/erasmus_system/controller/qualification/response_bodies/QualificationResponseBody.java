package pl.agh.edu.erasmus_system.controller.qualification.response_bodies;

import java.util.*;

/**
 * POJO class contains body of response /qualificationView
 */
public class QualificationResponseBody {
    private List<QualificationSingleResponseBody> contracts;
    private Map<Long, ArrayList<Long>> studentsRegistrations;

    public QualificationResponseBody() {
        this.contracts = new ArrayList<>();
        this.studentsRegistrations = new HashMap<>();
    }

    public void addContract(QualificationSingleResponseBody contract) {
        this.contracts.add(contract);
    }

    public void addStudentsRegistrations(Map<Long, ArrayList<Long>> studentsRegistrations) {
        this.studentsRegistrations = studentsRegistrations;
    }

    public List<QualificationSingleResponseBody> getContracts() {
        return contracts;
    }

    public Map<Long, ArrayList<Long>> getStudentsRegistrations() {
        return studentsRegistrations;
    }
}