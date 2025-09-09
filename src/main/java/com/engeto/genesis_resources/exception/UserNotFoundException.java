package com.engeto.genesis_resources.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found with ID " + id);
    }
}
