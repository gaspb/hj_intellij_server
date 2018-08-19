package fr.hija.gtw.web.rest.vm;

import java.io.Serializable;

public class PublicApiVM implements Serializable{

    private String name;
    private String desc;
    private Object input;
    private Object output;
    private String rest;
    private String URL;
    private String[] _params;

    public PublicApiVM(String name, String desc, Object input, Object output, String rest, String URL, String[] _params) {
        this.name = name;
        this.desc = desc;
        this.input = input;
        this.output = output;
        this.rest = rest;
        this.URL = URL;
        this._params = _params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getInput() {
        return input;
    }

    public void setInput(Object input) {
        this.input = input;
    }

    public Object getOutput() {
        return output;
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String[] get_params() {
        return _params;
    }

    public void set_params(String[] _params) {
        this._params = _params;
    }

    public PublicApiVM() {
    }
}
