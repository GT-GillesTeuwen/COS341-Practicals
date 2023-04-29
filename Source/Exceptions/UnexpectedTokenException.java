package Exceptions;

public class UnexpectedTokenException extends Exception {
    public UnexpectedTokenException(String errorMessage) {
        super(errorMessage);
    }
}
