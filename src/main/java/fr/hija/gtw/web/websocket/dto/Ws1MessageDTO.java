package fr.hija.gtw.web.websocket.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for storing a user's activity.
 */
public class Ws1MessageDTO implements Serializable{


    private String body;
    private String login;
    private String role;
    private Instant time;
    private String id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }



    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @Override
    public String toString() {
        return "Ws1MessageDTO{" +
            "body='" + body + '\'' +
            ", login='" + login + '\'' +
            ", role='" + role + '\'' +
            ", time=" + time +
            ", id='" + id + '\'' +
            '}';
    }
}
