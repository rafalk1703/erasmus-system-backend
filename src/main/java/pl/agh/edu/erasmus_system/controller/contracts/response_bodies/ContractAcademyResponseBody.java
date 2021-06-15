package pl.agh.edu.erasmus_system.controller.contracts.response_bodies;

import lombok.Getter;
import pl.agh.edu.erasmus_system.model.Academy;

@Getter
public class ContractAcademyResponseBody {
    private Long id;
    private String name;
    private String country;
    private String city;
    private String code;

    public ContractAcademyResponseBody(Academy academy) {
        this.id = academy.getId();
        this.name = academy.getName();
        this.country = academy.getCountry();
        this.city = academy.getCity();
        this.code = academy.getCode();
    }
}
