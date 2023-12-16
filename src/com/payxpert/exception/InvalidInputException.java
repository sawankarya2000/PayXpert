package com.payxpert.exception;

public class InvalidInputException extends Exception {

    public InvalidInputException() {
        super("Invalid input data.");
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
