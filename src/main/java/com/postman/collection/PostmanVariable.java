package com.postman.collection;
import com.google.gson.Gson;
import com.networknt.schema.ValidationMessage;

public class PostmanVariable extends PostmanCollectionElement {
    private String key = "";
    private String value = "";
    private String description;
    private String type = "string";
    
    
    public String getToken() {
        return "{{" + key + "}}";
    }

    public PostmanVariable(String key, String value, String description) {
        this(key,value,description,null);
        
    }

    public PostmanVariable(String key, String value, String description, String type) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.type = type;
    }

    public PostmanVariable(String key, String value) {
        this(key,value,null,null);
    }

    public String getKey() {
        return key;
    }

    public void setDescription(String desc)
    {
        this.description = desc;
    }

    public String getDescription() {
        return this.description;
    }




    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    
 

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
