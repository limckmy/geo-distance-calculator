package org.limckmy.geodistancecalculator.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class AuthException {
    private HttpStatus status;
    private String message;
    private String details;

    public AuthException(@JsonProperty("status") HttpStatus status,
                    @JsonProperty("message") String message,
                    @JsonProperty("details") String details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
