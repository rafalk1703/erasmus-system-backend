package pl.agh.edu.erasmus_system.controller.qualification.request_bodies;

import lombok.Getter;

@Getter
public class RegistrationRequestBody {
    private Long registrationId;
    private Boolean registrationStatus;

    public RegistrationRequestBody() {
    }
}