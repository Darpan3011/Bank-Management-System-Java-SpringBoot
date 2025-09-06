package com.darpan.finalbankmanagement.exception;

import org.springframework.http.HttpStatus;

public class InvalidAmountException extends BankException {
    public InvalidAmountException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "INVALID_AMOUNT");
    }
}
