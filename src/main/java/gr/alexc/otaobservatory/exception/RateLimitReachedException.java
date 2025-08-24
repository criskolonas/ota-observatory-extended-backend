package gr.alexc.otaobservatory.exception;

import org.springframework.security.core.AuthenticationException;

public class RateLimitReachedException extends AuthenticationException {
  public RateLimitReachedException(String message) {
    super(message);
  }
}
