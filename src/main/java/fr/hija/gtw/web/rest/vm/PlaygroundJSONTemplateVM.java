package fr.hija.gtw.web.rest.vm;

import java.io.Serializable;

public class PlaygroundJSONTemplateVM implements Serializable{

    private String jsonTemplate;

    public PlaygroundJSONTemplateVM(String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    public String getJsonTemplate() {
        return jsonTemplate;
    }

    public void setJsonTemplate(String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    public PlaygroundJSONTemplateVM() {
    }
}
