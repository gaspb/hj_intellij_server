package fr.hija.gtw.web.rest.vm;

import java.io.Serializable;

public class PlaygroundHTMLVM implements Serializable{
    private Object htmlTemplate;
    private Object htmlOperation;


    public Object getHtmlTemplate() {
        return htmlTemplate;
    }

    public void setHtmlTemplate(Object htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    public Object getHtmlOperation() {
        return htmlOperation;
    }

    public void setHtmlOperation(Object htmlOperation) {
        this.htmlOperation = htmlOperation;
    }
    public PlaygroundHTMLVM(Object htmlTemplate, Object htmlOperation) {
        this.htmlTemplate = htmlTemplate;
        this.htmlOperation = htmlOperation;
    }

    public PlaygroundHTMLVM() {
    }
}
