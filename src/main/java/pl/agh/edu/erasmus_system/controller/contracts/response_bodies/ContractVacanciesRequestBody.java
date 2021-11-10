package pl.agh.edu.erasmus_system.controller.contracts.response_bodies;

import lombok.Getter;
import pl.agh.edu.erasmus_system.controller.SessionRequestBody;

@Getter
public class ContractVacanciesRequestBody extends SessionRequestBody {

    private int vacancies;

    public ContractVacanciesRequestBody() {
    }
}
