package com.postman.collection;
/**
 * 
 * 
 * Exception thrown when an attempt is made to add a Property to a PropertyList with a duplicate key
 * 
 * 
 * 
 */
public class DuplicateVariableKeyException extends Exception{
    
    /**
     * 
     * 
     * Constructs the exception
     * 
     * @param message The message to send with the exception
     */
    public DuplicateVariableKeyException(String message) {
        super(message);
    }
}
