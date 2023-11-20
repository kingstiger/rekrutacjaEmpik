package com.rekrutacja.empik.errorhandler;

public class UnknownServiceError extends RuntimeException {

    public UnknownServiceError(String message) {
        super(message);
    }
}
