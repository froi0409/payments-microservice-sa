package com.froi.payments.common.exceptions;

public class DuplicatedEntityException extends Exception {
    public DuplicatedEntityException() {
    }

    public DuplicatedEntityException(String message) {
        super(message);
    }
}
