package gr.alexc.otaobservatory.exception;

public class CaptchaFailException extends RuntimeException {
    public CaptchaFailException(String message) {
        super(message);
    }
}
