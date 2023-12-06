package io.spring.movie.exception;

public class CustomBatchException extends RuntimeException {

    public CustomBatchException(String message) {
        super(message);
    }
}
