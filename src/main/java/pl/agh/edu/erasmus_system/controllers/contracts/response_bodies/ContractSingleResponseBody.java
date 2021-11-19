package pl.agh.edu.erasmus_system.controllers.contracts.response_bodies;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContractSingleResponseBody {

    private Long id;
    private ContractCoordinatorResponseBody contractCoordinator;
    private ContractEditionResponseBody contractEdition;
    private String erasmusCode;
    private Integer vacancies;
    private String degree;
    private String startYear;
    private String endYear;
    private String faculty;

}
