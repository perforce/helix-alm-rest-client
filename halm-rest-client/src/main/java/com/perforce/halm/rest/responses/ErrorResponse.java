package com.perforce.halm.rest.responses;

public class ErrorResponse {
    private String message;
    private Integer statusCode;
    private String code;
    private String errorElementPath;

    /**
     * @return Short description of error type. Typically just the text version of the HTTP status code error value.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Long description or message about the error
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * @return Error type identifier. An HTTP status code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Path to the object that caused an error if there was a problem sending data to the server. This may
     *         be null.
     */
    public String getErrorElementPath() {
        return errorElementPath;
    }
}
