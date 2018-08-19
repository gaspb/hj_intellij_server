package fr.hija.gtw.web.rest.vm;

import java.io.Serializable;

public class PlaygroundOperationDescVM implements Serializable{

    private String operationName;
    private String desc;
    private String input;
    private String output;

    public PlaygroundOperationDescVM(String operationName, String desc, String input, String output) {
        this.operationName = operationName;
        this.desc = desc;
        this.input = input;
        this.output = output;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public PlaygroundOperationDescVM() {
    }
}
