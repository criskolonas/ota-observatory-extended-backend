package gr.alexc.otaobservatory.exception;


import org.springframework.security.core.AuthenticationException;

public class RestrictedAccessException extends AuthenticationException {
    public RestrictedAccessException(String message) {
        super(message);
    }
}
