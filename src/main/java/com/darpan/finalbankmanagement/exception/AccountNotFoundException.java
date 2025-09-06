package com.darpan.finalbankmanagement.exception;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends BankException {
    public AccountNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "ACCOUNT_NOT_FOUND");
    }
}
