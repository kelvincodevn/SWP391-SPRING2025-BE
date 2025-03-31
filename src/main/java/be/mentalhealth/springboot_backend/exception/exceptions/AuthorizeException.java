package be.mentalhealth.springboot_backend.exception.exceptions;

public class AuthorizeException extends RuntimeException {

    public AuthorizeException(String message) {
        super(message);
    }
}