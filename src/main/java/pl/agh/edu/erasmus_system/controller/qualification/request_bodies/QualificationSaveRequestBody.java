package pl.agh.edu.erasmus_system.controller.qualification.request_bodies;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class QualificationSaveRequestBody {
    private ArrayList<RegistrationRequestBody> registrations;

    public QualificationSaveRequestBody() {
    }
}