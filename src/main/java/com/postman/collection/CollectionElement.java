package com.postman.collection;

import com.networknt.schema.ValidationMessage;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.networknt.schema.JsonSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchemaFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Set;

import com.networknt.schema.SpecVersion;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import com.flipkart.zjsonpatch.*;
/**
 * Abstract Base Class for all objects which are part of a collection
 * 
 */
public abstract class CollectionElement {

    private transient ArrayList<ValidationMessage> validationMessages;
    private static final String defaultCollectionSchema = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json";
    private static final String defaultValidationSchema = "https://schema.postman.com/collection/json/v2.1.0/draft-07/collection.json";
    private transient UUID uuid = UUID.randomUUID();
    private transient CollectionElement parent;

    public abstract String getKey();

    
    /** 
     * 
     * 
     * Validate the gson produced by this element against the PostmanSchema.  Schema version is currently hardcoded to 
     * <a href="https://schema.postman.com/collection/json/v2.1.0/draft-07/collection.json">v2.1.0</a>.  Validation is provided by the <a href="https://github.com/networknt/json-schema-validator">NetworkNT json-schema-validator</a>
     * 
     * @return boolean <code>true</code> if valid, <code>false</code> if not.  If the schema is invalid, calling {@link com.postman.collection.Collection#getValidationMessages()  } will return an  containing the diffs
     * @throws ValidationException If an error is encountered accessing the schema or mapping the underlying JSON.  
     */
    public boolean validate() throws ValidationException {
        return this.validate(null);
    }

    
    
    /** 
     * 
     * Set the parent of this element, allowing for traversal up the chain of elements
     * 
     * 
     * @param parent The parent element
     */
    public void setParent(CollectionElement parent) {
        this.parent = parent;
    }

    
    /** 
     * 
     * Get the parent element, or null if it is not set.
     * 
     * @return CollectionElement The parent of this element
     */
    public CollectionElement getParent() {
        return this.parent;
    }

    /** 
     * 
     * Convenience method allowing validation against a user-provided schema
     * 
     * @param altSchemaJSON  String containing the alternate schema JSON
     * @return boolean <code>true</code> if valid, <code>false</code> if not.  If the schema is invalid, calling {@link com.postman.collection.Collection#getValidationMessages()  } will return an  containing the diffs
     * @throws ValidationException If there is an error in the validation process
     */
    public boolean validate(String altSchemaJSON) throws ValidationException {

        ObjectMapper mapper = new ObjectMapper();
        JsonSchema schema;

        String strSchemaRoot = CollectionElement.getDefaultValidationSchema();
        String strSubSchema = "";
        switch (this.getClass().getSimpleName()) {
            case "Item": {
                strSubSchema = "#/definitions/item";
                break;
            }
            case "Collection": {
                strSubSchema = "";
                break;
            }

            case "RequestAuth": {
                strSubSchema = "#/definitions/auth";
                break;
            }
            case "BodyElement": {
                strSubSchema = "#/definitions/request/oneOf/0/properties/body";
                break;
            }
            default: {
                strSubSchema = "";
            }
        }

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        if (altSchemaJSON == null) {
            try {
                schema = factory.getSchema(new URI(strSchemaRoot + strSubSchema));
            }
            catch(URISyntaxException e) {
                throw new ValidationException(e);
            }
            
        } else {
            schema = factory.getSchema(altSchemaJSON);
        }
        JsonNode pmcNode;
        
            try {
                pmcNode = mapper.readTree(this.toJson());
            }
            catch(JsonProcessingException e) {
                throw new ValidationException(e);
            }
            
        

        schema.initializeValidators();
        Set<ValidationMessage> errors = schema.validate(pmcNode);

        this.validationMessages = (errors == null || errors.size() == 0) ? new ArrayList<ValidationMessage>()
                : new ArrayList<ValidationMessage>(errors);
                boolean ignoreValidationErrors = true;
                
                
                //This hackery necessitated by the fact that collections returned from Postman can have 'default' as the type
                //for a value in the collection variables, which is not in the schema.
                //So we ignore it as an error if it is present.  
                String valMsg = "";
                String valPath = "";
                for(ValidationMessage vm : this.validationMessages )
                {
                    valMsg = vm.getMessage();
                    valPath = vm.getPath();

                    ignoreValidationErrors = ignoreValidationErrors && valMsg.contains("$.variable[") && valMsg.contains(".type: does not have a value in the enumeration [string, boolean, any, number]");
                }
        return ((this.validationMessages == null || this.validationMessages.size() == 0) || ignoreValidationErrors);

    }

    
    /** 
     * 
     * If an element is invalid, returns an ArrayList&#60;ValidationMessage&#62; containing one or more diff messages describing the differences.  If the element is valid
     * the size of the returned ArrayList will be zero.
     * 
     * @return ArrayList&#60;<a href="https://javadoc.io/doc/com.networknt/json-schema-validator/1.0.51/com/networknt/schema/ValidationMessage.html">ValidationMessage&#62;</a> An ArrayList containing zero or more validatin messages.
     */
    public ArrayList<ValidationMessage> getValidationMessages() {
        return this.validationMessages;
    }

    
    /** 
     * 
     * Render this element to JSON using the Gson conversion library.  
     * 
     * @return String The JSON rendered by Gson
     */
    public String toJson() {
        return this.toJson(false, false);
    }

    
    /** 
     * 
     * Render this element to JSON using the Gson conversion library. The <code>escape</code> and <code>resolveVariable</code> arguments are currently not implemented
     * 
     * 
     * @param escape NOT IMPLEMENTED: Escaping scheme for JSON
     * @param resolveVariables NOT IMPLEMENTED: Whether to resolve variables to their corresponding values.  
     * @return String The rendered JSON
     */
    public String toJson(boolean escape, boolean resolveVariables) {
        return new Gson().toJson(this);
    }

    
    /** 
     * 
     * Get a unique UUID for this element.  UUIDs are not persisted/serialized
     * 
     * @return UUID A unique UUID
     */
    public UUID getUUID() {
        return this.uuid;
    }

    
    /** 
     * 
     * Set a new unique UUID for this element.
     * 
     * @param newID A new UUID
     */
    public void setUUID(UUID newID) {
        this.uuid = newID;
    }

    
    /** 
     * 
     * Traverse the chain of parent elements upwards until a Collection is reached, or null if this element is not part of a collection.
     * 
     * 
     * @return Collection The collection at the top of the parent tree, or null.
     */
    public Collection getCollection() {
        CollectionElement result = null;
        CollectionElement curItem = null;
        // recursively traverse items looking for name == key
        if(this.getParent() == null) {
            return this instanceof Collection ? (Collection)this : null;
        }
        while(result == null) {
            curItem = this.getParent();
            if (curItem instanceof Collection) {
                result = curItem;
                break;
            } else {
                try {
                    result = curItem.getCollection();
                    if(result == null) {
                        return null;
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                
                if (result instanceof Collection) {
                    break;
                }
            }
        }
        

        return (Collection)result;
    }

    
    /** 
     * 
     * Determine whether 2 seperate instance of a JPostman class render semantically identical JSON.  If the JSON is identical, the returned JsonNode will be empty (e.g., <code>size()</code> == 0).
     * If the documents are different, the JsonNode returned contains information about the differences.  
     * 
     * @param compare The CollectionElement to compare to this one.
     * @return JsonNode JsonNode containing an array of diff msgs.  size() will be zero if there are no validation messages.
     * @throws ValidationException If there is an exception or error during the Validation process
     */
    public JsonNode isEquivalentTo(CollectionElement compare) throws ValidationException {
        ObjectMapper jacksonObjectMapper;
        JsonNode beforeNode;
        JsonNode afterNode;
        JsonNode patch;
        
        jacksonObjectMapper = new ObjectMapper();
        try {
            beforeNode = jacksonObjectMapper.readTree(this.toJson());
            afterNode = jacksonObjectMapper.readTree(compare.toJson());
        }
        catch(Exception e) {
            throw new ValidationException(e);
        }
            
        patch = JsonDiff.asJson(beforeNode, afterNode);

        return patch;
    }
    /**
     * 
     * <p>Return the default schema appearing in the <code>schema</code> property in exported collections.  </p>
     * <p>
     * <p>Current: https://schema.getpostman.com/json/collection/v2.1.0/collection.json</p>
     * 
     * @return The URI for default schema used to validate schemas
     */
    public static String getDefaultCollectionSchema() {
        return defaultCollectionSchema;
    }
    /**
     * 
     * <p>Return the default schema used to validate schemas.  NOTE: AS of version Version 10.8.9 this is not the same 
     * appearing in the <code>schema</code> property in exported collections.  </p>
     * <p>
     * <p>Current: https://schema.postman.com/collection/json/v2.1.0/draft-07/collection.json</p>
     * 
     * @return The URI for default schema used to validate schemas
     */
    public static String getDefaultValidationSchema() {
        return defaultValidationSchema;
    }
}