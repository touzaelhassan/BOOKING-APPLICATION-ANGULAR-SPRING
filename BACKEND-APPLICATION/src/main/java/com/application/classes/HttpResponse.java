package com.application.classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class HttpResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "GMT+1")
    private Date timeStamp;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;

    public HttpResponse() { }

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.timeStamp = new Date();
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }

    public void setHttpStatusCode(int httpStatusCode) { this.httpStatusCode = httpStatusCode; }
    public void setHttpStatus(HttpStatus httpStatus) { this.httpStatus = httpStatus; }
    public void setReason(String reason) { this.reason = reason; }
    public void setMessage(String message) { this.message = message; }
    public void setTimeStamp(Date timeStamp) { this.timeStamp = timeStamp; }

    public int getHttpStatusCode() { return httpStatusCode; }
    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getReason() { return reason; }
    public String getMessage() { return message; }
    public Date getTimeStamp() { return timeStamp; }

}
