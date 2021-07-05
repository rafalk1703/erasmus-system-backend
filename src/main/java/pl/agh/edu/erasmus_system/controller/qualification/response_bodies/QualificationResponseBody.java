package pl.agh.edu.erasmus_system.controller.qualification.response_bodies;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO class contains body of response /qualificationView
 */
public class QualificationResponseBody {
    private List<QualificationSingleResponseBody> contracts;

    public QualificationResponseBody() {
        this.contracts = new ArrayList<>();
    }

    public void add(QualificationSingleResponseBody contract) {
        this.contracts.add(contract);
    }

    public List<QualificationSingleResponseBody> getBody() {
        return contracts;
    }

    public void setBody(List<QualificationSingleResponseBody> body) {
        this.contracts = body;
    }
}