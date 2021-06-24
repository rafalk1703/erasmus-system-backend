package pl.agh.edu.erasmus_system.controller.contracts.response_bodies;

import lombok.Getter;
import pl.agh.edu.erasmus_system.model.Edition;

@Getter
public class ContractEditionResponseBody {

    private Long id;
    private String year;

    public ContractEditionResponseBody(Edition edition) {
        this.id = edition.getId();
        this.year = edition.getYear();
    }
}
