package pl.agh.edu.erasmus_system.controller.contract_coordinators.response_bodies;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContractCoordinatorSingleResponseBody {
    private Long id;
    private String name;
    private String email;
    private String code;

}
