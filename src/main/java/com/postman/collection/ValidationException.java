package com.postman.collection;

import java.util.ArrayList;
import com.networknt.schema.ValidationMessage;

public class ValidationException extends Exception {
    
    public ValidationException(Throwable cause) {
        super("Validation Exception", cause);
    }

    public ValidationException(String message) {
        super(message);
        
    }

}
