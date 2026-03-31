package br.dev.as.user_service;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final Long id) {
        super("User not found with id: " + id);
    }

    public UserNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}