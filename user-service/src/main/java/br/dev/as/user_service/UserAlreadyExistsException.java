package br.dev.as.user_service;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(final String email) {
        super("User already exists with email: " + email);
    }

    public UserAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
