package com.postman.collection;

public class ValidationException extends Exception {
    
    public ValidationException(Throwable cause) {
        super("Validation Exception", cause);
    }

}
