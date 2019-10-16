package registry.exceptions;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorResponse {
    private int status;
    private String message;
    private String timeStamp;

    public ErrorResponse() { }

    public ErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.setTimeStamp(timeStamp);
    }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getTimeStamp() { return timeStamp; }

    public void setTimeStamp(long timeStamp) {
        Timestamp ts = new Timestamp(timeStamp);
        Date date = new Date();
        date.setTime(ts.getTime());
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(date);
    }
}
