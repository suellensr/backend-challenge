package edu.challangetwo.orderapi.exception;

public class InvalidPriceOrQuantityException extends RuntimeException {
    public InvalidPriceOrQuantityException(String message) {
        super(message);
    }
}
