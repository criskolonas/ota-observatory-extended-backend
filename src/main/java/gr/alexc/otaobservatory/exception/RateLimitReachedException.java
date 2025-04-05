package gr.alexc.otaobservatory.exception;

public class RateLimitReachedException extends RuntimeException {
  public RateLimitReachedException(String message) {
    super("Call limit reached. Try again later.");
  }
}
