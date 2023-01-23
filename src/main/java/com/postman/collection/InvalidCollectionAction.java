package com.postman.collection;

public class InvalidCollectionAction extends Exception {
    public InvalidCollectionAction(String message) {
        super(message);
    }

    public InvalidCollectionAction(Throwable cause) {
        super(cause);
    }
}
