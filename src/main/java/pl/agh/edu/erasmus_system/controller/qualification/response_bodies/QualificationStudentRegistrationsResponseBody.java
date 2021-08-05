package pl.agh.edu.erasmus_system.controller.qualification.response_bodies;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class QualificationStudentRegistrationsResponseBody {
    private ArrayList<Long> registrationsIds;
    private Long acceptedAmount;

    public QualificationStudentRegistrationsResponseBody() {
        this.registrationsIds = new ArrayList<>();
        this.acceptedAmount = 0L;
    }

    public void addRegistrationId(Long registrationId) {
        this.registrationsIds.add(registrationId);
    }

    public void increaseAcceptedAmount() {
        this.acceptedAmount++;
    }
}