package fr.hija.gtw.web.rest.vm;

import java.io.Serializable;

public class PlaygroundOperationDTO implements Serializable{

    private PlaygroundJSONOperationVM json;
    private PlaygroundOperationDescVM desc;

    public PlaygroundOperationDTO(PlaygroundJSONOperationVM json, PlaygroundOperationDescVM desc) {
        this.json = json;
        this.desc = desc;
    }

    public PlaygroundJSONOperationVM getJson() {
        return json;
    }

    public void setJson(PlaygroundJSONOperationVM json) {
        this.json = json;
    }

    public PlaygroundOperationDescVM getDesc() {
        return desc;
    }

    public void setDesc(PlaygroundOperationDescVM desc) {
        this.desc = desc;
    }

    public PlaygroundOperationDTO() {
    }
}
