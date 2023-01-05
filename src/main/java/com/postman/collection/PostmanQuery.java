package com.postman.collection;
import com.google.gson.Gson;
public class PostmanQuery implements IPostmanCollectionElement {
    private String key = "";
    private String value = "";
    private String description = "";

    public PostmanQuery(String key, String value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    public String getKey() {
        return key;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        // TODO Auto-generated method stub
        return new Gson().toJson(this);
    }

    
 
    
    
}
