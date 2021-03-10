package net.carlosfe.tests.sintad.global.models;

import java.io.Serializable;

public class CustomFieldError implements Serializable {

    private static final long serialVersionUID = 7405063765088202355L;
    private String field;
    private String message;

    public CustomFieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String code) {
        this.field = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
