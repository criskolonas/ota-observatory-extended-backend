package gr.alexc.otaobservatory.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<?> clazz, Long id) {
        super("Resource not found: " + clazz.getSimpleName() + " with id: " + id);
    }
}
