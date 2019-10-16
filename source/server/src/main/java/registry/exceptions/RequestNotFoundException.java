package registry.exceptions;

public class RequestNotFoundException extends NotFoundException {
    public RequestNotFoundException(String item) {
        super("Request not found: " + item);
    }
}
