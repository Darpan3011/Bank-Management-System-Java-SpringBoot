package com.darpan.finalbankmanagement.exception;

import org.springframework.http.HttpStatus;

public class TransactionLimitExceededException extends BankException {
    public TransactionLimitExceededException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "TRANSACTION_LIMIT_EXCEEDED");
    }
}
