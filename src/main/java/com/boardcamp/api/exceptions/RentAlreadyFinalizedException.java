package com.boardcamp.api.exceptions;

public class RentAlreadyFinalizedException extends RuntimeException {
    public RentAlreadyFinalizedException(String message) {
        super(message);
    }
}
