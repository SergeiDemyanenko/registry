package registry.exceptions;

public class ModelNotFoundException extends NotFoundException {
    public ModelNotFoundException(String model) {
        super("Model not found: " + model);
    }
}
