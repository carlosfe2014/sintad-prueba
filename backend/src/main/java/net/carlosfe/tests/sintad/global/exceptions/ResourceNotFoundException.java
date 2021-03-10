package net.carlosfe.tests.sintad.global.exceptions;


public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 470920798555669888L;
    public ResourceNotFoundException(String message) { super(message); }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
