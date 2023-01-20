package com.postman.collection;
import com.networknt.schema.ValidationMessage;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.networknt.schema.ValidationMessage;
import java.util.ArrayList;
import com.networknt.schema.JsonSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchemaFactory;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Set;

import com.networknt.schema.SpecVersion;
import java.net.URI;

public abstract class PostmanCollectionElement {

    private ArrayList<ValidationMessage> validationMessages;
    public abstract String getKey();
    
    public boolean validate() throws Exception{
        return this.validate(null);
    }
    public boolean validate(String altSchemaJSON) throws Exception {
        

        ObjectMapper mapper = new ObjectMapper();
        JsonSchema schema;
        
        
        String strSchemaRoot = "https://schema.postman.com/collection/json/v2.1.0/draft-07/collection.json";
        String strSubSchema = "";
        switch(this.getClass().getSimpleName()) {
            case "PostmanItem" : {
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
        }

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        if(altSchemaJSON == null)
        {
            schema = factory.getSchema(new URI(strSchemaRoot + strSubSchema));
        }
        else
        {
            schema = factory.getSchema(altSchemaJSON);
        }
        
        JsonNode pmcNode = mapper.readTree(this.toJson());
        schema.initializeValidators();
        Set<ValidationMessage> errors = schema.validate(pmcNode);
        
        this.validationMessages = (errors == null || errors.size() == 0) ? new ArrayList<ValidationMessage>() : new ArrayList<ValidationMessage>(errors);
        return (this.validationMessages == null || this.validationMessages.size() == 0);
    
    }
    

    public ArrayList<ValidationMessage> getValidationMessages() {
        return this.validationMessages;
    }

    public String toJson() {
        return this.toJson(false,false);
    }

    public String toJson(boolean escape, boolean resolveVariables) {
        return new Gson().toJson(this);
    }
    
    
}