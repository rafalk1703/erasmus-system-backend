package pl.agh.edu.erasmus_system.controllers.qualification.response_bodies;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class QualificationStudentRegistrationsResponseBody {
    private ArrayList<Long> registrationsIds;
    private Long tickedAmount;

    public QualificationStudentRegistrationsResponseBody() {
        this.registrationsIds = new ArrayList<>();
        this.tickedAmount = 0L;
    }

    public void addRegistrationId(Long registrationId) {
        this.registrationsIds.add(registrationId);
    }

    public void increaseTickedAmount() {
        this.tickedAmount++;
    }
}