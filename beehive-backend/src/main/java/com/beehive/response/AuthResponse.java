package com.beehive.response;

import java.io.Serial;
import java.io.Serializable;

public class AuthResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 2982683243788075002L;

    private String token;
    private String statusMessage;

    public AuthResponse() {
    }

    public AuthResponse(String token, String statusMessage) {
        this.token = token;
        this.statusMessage = statusMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
