package com.payxpert.exception;

public class DatabaseConnectionException extends Exception {

    public DatabaseConnectionException() {
        super("Error with database connection.");
    }

    public DatabaseConnectionException(String message) {
        super(message);
    }
}

