package pl.agh.edu.erasmus_system.controllers.editions.response_bodies;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IfCanDownloadEditionSingleResponseBody {

    private Long id;
    private String ifCanDownloadNoWieit;
    private String ifCanDownloadWieit;
}
