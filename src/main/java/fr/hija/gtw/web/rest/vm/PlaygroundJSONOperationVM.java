package fr.hija.gtw.web.rest.vm;

import java.io.Serializable;

public class PlaygroundJSONOperationVM implements Serializable{

    private String operationName;
    private Object jsonOperation;

    public PlaygroundJSONOperationVM(String operationName, Object jsonOperation) {
        this.operationName = operationName;
        this.jsonOperation = jsonOperation;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Object getJsonOperation() {
        return jsonOperation;
    }

    public void setJsonOperation(Object jsonOperation) {
        this.jsonOperation = jsonOperation;
    }

    public PlaygroundJSONOperationVM() {
    }
}
