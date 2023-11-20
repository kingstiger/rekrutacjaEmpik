package com.rekrutacja.empik.errorhandler;

public class WrongLoginException extends RuntimeException {

    public WrongLoginException(String message) {
        super(message);
    }
}
