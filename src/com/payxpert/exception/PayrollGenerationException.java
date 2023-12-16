package com.payxpert.exception;

public class PayrollGenerationException extends Exception {

    public PayrollGenerationException() {
        super("Error generating payroll.");
    }

    public PayrollGenerationException(String message) {
        super(message);
    }
}
