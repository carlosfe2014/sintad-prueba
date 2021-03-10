package net.carlosfe.tests.sintad.global.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "error", "errorDebug", "fieldErrors", "timestamp" })
public class ErrorResponse {
    @JsonProperty("error")
    private String message;
    @JsonProperty("errorDebug")
    private String errorDebug;
    @JsonProperty("fieldErrors")
    private List<CustomFieldError> errors;
    private Date timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = new Date();
    }

    public ErrorResponse(String message, String originalMessage) {
        this.message = message;
        this.errorDebug = originalMessage;
        this.timestamp = new Date();
    }

    public ErrorResponse(String message, String originalMessage, List<CustomFieldError> errors) {
        this.message = message;
        this.errorDebug = originalMessage;
        this.errors = errors;
        this.timestamp = new Date();
    }

    public ErrorResponse(String message, List<CustomFieldError> errors) {
        this.message = message;
        this.errors = errors;
        this.timestamp = new Date();
    }

    public ErrorResponse() {
        this.timestamp = new Date();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorDebug() {
        return errorDebug;
    }

    public void setErrorDebug(String errorDebug) {
        this.errorDebug = errorDebug;
    }

    public List<CustomFieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<CustomFieldError> errors) {
        this.errors = errors;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
