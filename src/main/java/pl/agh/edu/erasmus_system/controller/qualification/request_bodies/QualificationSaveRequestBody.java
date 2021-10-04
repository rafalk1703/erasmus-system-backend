package pl.agh.edu.erasmus_system.controller.qualification.request_bodies;

import lombok.Getter;
import pl.agh.edu.erasmus_system.controller.SessionRequestBody;

import java.util.ArrayList;

@Getter
public class QualificationSaveRequestBody extends SessionRequestBody {
    private ArrayList<RegistrationRequestBody> registrations;

    public QualificationSaveRequestBody() {
    }
}