package registry.exceptions;

public class ModelNotFoundException extends NotFoundException {

    public ModelNotFoundException() { super(); }

    public ModelNotFoundException(String message) { super(message); }

    public ModelNotFoundException(String message, Throwable cause) { super(message, cause); }

    public ModelNotFoundException(Throwable cause) { super(cause); }

    public ModelNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
