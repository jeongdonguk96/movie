package io.spring.movie.exception;

public class CustomApiException extends RuntimeException {

    public CustomApiException(String message) {
        super(message);
    }
}
