package br.com.lgs.accounting.rest.payload;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private List<FIeldsErrorsResponse> messages;

    public ErrorMessageResponse(LocalDateTime timestamp, Integer status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorMessageResponse(LocalDateTime timestamp, Integer status, String error, List<FIeldsErrorsResponse> messages) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.messages = messages;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<FIeldsErrorsResponse> getMessages() {
        return messages;
    }
}
