package br.com.lgs.accounting.payload;

public class FIeldsErrorsResponse {
    private String field;
    private String message;

    public FIeldsErrorsResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
