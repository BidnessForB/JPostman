package com.postman.collection;

import com.networknt.schema.ValidationMessage;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.networknt.schema.JsonSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchemaFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
public abstract class PostmanCollectionElement {

    private transient ArrayList<ValidationMessage> validationMessages;
    public static final String defaultCollectionSchema = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json";
    public static final String defaultValidationSchema = "https://schema.postman.com/collection/json/v2.1.0/draft-07/collection.json";
    private transient UUID uuid = UUID.randomUUID();

    public abstract String getKey();

    
    /** 
     * 
     * 
     * Validate the gson produced by this element against the PostmanSchema.  Schema version is currently hardcoded to 
     * <a href="https://schema.postman.com/collection/json/v2.1.0/draft-07/collection.json">v2.1.0</a>.  Validation is provided by the <a href="https://github.com/networknt/json-schema-validator">NetworkNT json-schema-validator</a>
     * 
     * @return boolean <code>true</code> if valid, <code>false</code> if not.  If the schema is invalid, call getValidationMessages() will return a JsonElement containing the diffs
     * @throws ValidationException If an error is encountered accessing the schema or mapping the underlying JSON.  
     */
    public boolean validate() throws ValidationException {
        return this.validate(null);
    }

    
    /** 
     * 
     * Convenience method allowing validation against a user-provided schema
     * 
     * @param altSchemaJSON  String containing the alternate schema JSON
     * @return boolean True if valid, False if not.  If not valid, getValidationMessages() will return a JsonNode containing one or more diff messages.
     * @throws Exception
     */
    public boolean validate(String altSchemaJSON) throws ValidationException {

        ObjectMapper mapper = new ObjectMapper();
        JsonSchema schema;

        String strSchemaRoot = PostmanCollectionElement.defaultValidationSchema;
        String strSubSchema = "";
        switch (this.getClass().getSimpleName()) {
            case "PostmanItem": {
                strSubSchema = "#/definitions/item";
                break;
            }
            case "PostmanCollection": {
                strSubSchema = "";
                break;
            }

            case "PostmanAuth": {
                strSubSchema = "#/definitions/auth";
            }
            case "PostmanBody": {
                strSubSchema = "#/definitions/request/oneOf/0/properties/body";
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
        return (this.validationMessages == null || this.validationMessages.size() == 0);

    }

    
    /** 
     * 
     * If an element is invalid, returns an ArrayList&#60;ValidationMessage&#62; containing one or more diff messages describing the differences.  If the element is valid
     * the size of the returned ArrayList will be zero.
     * 
     * @return ArrayList&#60;ValidationMessage&#62; An ArrayList containing zero or more validatin messages.
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
     * Determine whether 2 seperate instance of a JPostman class render the same JSON.  If the JSON is identical, the returned JsonNode will be empty (e.g., <code>size()</code> == 0).
     * If the documents are different, the JsonNode returned contains information about the differences.  
     * 
     * @param compare
     * @return JsonNode
     * @throws Exception
     */
    public JsonNode isEquivalentTo(PostmanCollectionElement compare) throws ValidationException {
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
}