package com.darpan.finalbankmanagement.exception;

import org.springframework.http.HttpStatus;

public class InsufficientFundsException extends BankException {
    public InsufficientFundsException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "INSUFFICIENT_FUNDS");
    }
}
