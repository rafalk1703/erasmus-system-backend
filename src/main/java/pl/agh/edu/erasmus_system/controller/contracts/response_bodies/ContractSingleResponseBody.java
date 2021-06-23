package pl.agh.edu.erasmus_system.controller.contracts.response_bodies;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContractSingleResponseBody {

    private Long id;
    private ContractAcademyResponseBody contractAcademy;
    private ContractCoordinatorResponseBody contractCoordinator;
    private ContractEditionResponseBody contractEdition;
    private String erasmusCode;
    private Long vacancies;
    private String degree;
    private Long startYear;
    private Long endYear;

}
