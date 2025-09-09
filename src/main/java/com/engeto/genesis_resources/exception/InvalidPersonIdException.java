package com.engeto.genesis_resources.exception;

public class InvalidPersonIdException extends RuntimeException {

    public InvalidPersonIdException(String id) {
        super("Person ID '" + id + "' is not valid.");
    }
}
