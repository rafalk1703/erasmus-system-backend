package pl.agh.edu.erasmus_system.controllers.qualification.request_bodies;

import lombok.Getter;
import pl.agh.edu.erasmus_system.controllers.SessionRequestBody;

import java.util.ArrayList;

@Getter
public class QualificationSaveRequestBody extends SessionRequestBody {
    private ArrayList<RegistrationRequestBody> registrations;
    private String typeOfSaving;

    public QualificationSaveRequestBody() {
    }
}