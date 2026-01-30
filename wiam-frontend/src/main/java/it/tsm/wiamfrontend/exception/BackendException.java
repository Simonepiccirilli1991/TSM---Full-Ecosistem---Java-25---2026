package it.tsm.wiamfrontend.exception;

import lombok.Getter;

@Getter
public class BackendException extends RuntimeException {
    private final int statusCode;
    private final String errorCode;
    private final String errorDescription;

    public BackendException(int statusCode, String errorCode, String message, String errorDescription) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public BackendException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = null;
        this.errorDescription = null;
    }
}
