package registry.exceptions;

public class ReportNotFoundException extends NotFoundException {
    public ReportNotFoundException(String report) {
        super("Report not found: " + report);
    }
}
