package registry.exceptions;

public class NotFoundException extends RuntimeException {
    NotFoundException() { super(); }

    NotFoundException(String message) { super(message); }

    NotFoundException(String message, Throwable cause) { super(message + " because of: \n" + cause.toString()); }

    NotFoundException(Throwable cause) { super(cause); }

    NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
