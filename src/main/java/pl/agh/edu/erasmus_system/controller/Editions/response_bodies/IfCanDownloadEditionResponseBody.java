package pl.agh.edu.erasmus_system.controller.Editions.response_bodies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.agh.edu.erasmus_system.controller.contracts.response_bodies.ContractSingleResponseBody;

import java.util.ArrayList;
import java.util.List;


public class IfCanDownloadEditionResponseBody {

    private List<IfCanDownloadEditionSingleResponseBody> editions;

    public IfCanDownloadEditionResponseBody() {
        this.editions = new ArrayList<>();
    }

    public void add(IfCanDownloadEditionSingleResponseBody edition) {
        this.editions.add(edition);
    }

    public List<IfCanDownloadEditionSingleResponseBody> getBody() {
        return editions;
    }

    public void setBody(List<IfCanDownloadEditionSingleResponseBody> body) {
        this.editions = body;
    }

}
