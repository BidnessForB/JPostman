package com.postman.collection;

public class InvalidCollectionActionException extends Exception {
    public InvalidCollectionActionException(String message) {
        super(message);
    }

    public InvalidCollectionActionException(Throwable cause) {
        super(cause);
    }
}
