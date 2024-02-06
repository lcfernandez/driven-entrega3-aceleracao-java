package com.boardcamp.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {
    // Customer
    @ExceptionHandler({ CustomerCpfConflictException.class})
    public ResponseEntity<String> handleCustomerCpfConflict(CustomerCpfConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({ CustomerNotFoundException.class})
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    // Game
    @ExceptionHandler({ GameNameConflictException.class })
    public ResponseEntity<String> handleGameNameConflict(GameNameConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({ GameNotAvailableException.class })
    public ResponseEntity<String> handleGameNotAvailable(GameNotAvailableException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler({ GameNotFoundException.class })
    public ResponseEntity<String> handleGameNotFound(GameNotFoundException exception) {
        return ResponseEntity.status((HttpStatus.NOT_FOUND)).body(exception.getMessage());
    }

    // Rent
    @ExceptionHandler({ RentAlreadyFinalizedException.class })
    public ResponseEntity<String> handleRentAlreadyFinalized(RentAlreadyFinalizedException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler({ RentNotFoundException.class })
    public ResponseEntity<String> handleRentNotFound(RentNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
