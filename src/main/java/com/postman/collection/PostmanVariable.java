package com.postman.collection;
import com.google.gson.Gson;
public class PostmanVariable implements IPostmanCollectionElement {
    private String key = "";
    private String value = "";
    private String description = "";
    private String type = "";
    
    
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

    public boolean isValid() {
        return true;
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

    
    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        
        return new Gson().toJson(this);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
