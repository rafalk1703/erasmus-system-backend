package pl.agh.edu.erasmus_system.controller.qualification.request_body;

import lombok.Getter;

@Getter
public class RegistrationRequestBody {
    private Long registrationId;
    private Boolean acceptanceStatus;

    public RegistrationRequestBody() {
    }
}