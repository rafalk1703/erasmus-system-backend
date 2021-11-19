package pl.agh.edu.erasmus_system.controllers.editions.response_bodies;

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
