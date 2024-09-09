package com.froi.payments.common.exceptions;

public class InvalidEntityFormatException extends Exception {
    public InvalidEntityFormatException() {
    }

    public InvalidEntityFormatException(String message) {
        super(message);
    }
}
