package com.darpan.finalbankmanagement.exception;

import org.springframework.http.HttpStatus;

public class BankException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;
    private final String timestamp;

    public BankException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public BankException(String message, HttpStatus status) {
        this(message, status, "BANK_ERROR");
    }
}
