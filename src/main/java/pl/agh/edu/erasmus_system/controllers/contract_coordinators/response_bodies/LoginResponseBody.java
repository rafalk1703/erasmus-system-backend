package pl.agh.edu.erasmus_system.controllers.contract_coordinators.response_bodies;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * POJO class contains body for /login response
 */
@Getter
@AllArgsConstructor
public class LoginResponseBody {

    private String sessionCode;
    private String coordinatorRole;
}
