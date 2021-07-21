package pl.agh.edu.erasmus_system.controller.Editions.response_bodies;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EditionStatisticsResponseBody {

    private Long id;
    private String year;
    private int numberOfContracts;
    private int numberOfRegistrations;
    private int numberOfContracts1Degree;
    private int numberOfContracts2Degree;
    private int numberOfContracts3Degree;
    private int numberOfRegistrations1Degree;
    private int numberOfRegistrations2Degree;
    private int numberOfRegistrations3Degree;
}
