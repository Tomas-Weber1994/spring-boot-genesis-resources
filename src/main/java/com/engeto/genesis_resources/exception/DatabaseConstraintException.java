package com.engeto.genesis_resources.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DatabaseConstraintException extends RuntimeException {

    public DatabaseConstraintException(DataIntegrityViolationException e) {
        super("Database constraint violated: " + e.getMostSpecificCause().getMessage(), e);
    }
}
