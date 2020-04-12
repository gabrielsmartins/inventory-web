package br.com.inventory.web.exception;

public class InvalidOrderItemException extends RuntimeException {

    public InvalidOrderItemException(String message) {
        super(message);
    }
}
