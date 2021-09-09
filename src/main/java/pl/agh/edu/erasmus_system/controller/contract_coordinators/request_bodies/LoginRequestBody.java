package pl.agh.edu.erasmus_system.controller.contract_coordinators.request_bodies;

import lombok.Getter;

/**
 * POJO class contains body of request api/login
 */
@Getter
public class LoginRequestBody {
    private String email;
    private String password;

    public LoginRequestBody() {}
}