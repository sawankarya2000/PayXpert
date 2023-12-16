package com.payxpert.exception;

public class FinancialRecordException extends Exception {

    public FinancialRecordException() {
        super("Error managing financial records.");
    }

    public FinancialRecordException(String message) {
        super(message);
    }
}
