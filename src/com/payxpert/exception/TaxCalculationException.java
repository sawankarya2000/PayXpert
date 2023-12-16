package com.payxpert.exception;

public class TaxCalculationException extends Exception {

    public TaxCalculationException() {
        super("Error calculating taxes.");
    }

    public TaxCalculationException(String message) {
        super(message);
    }
}
