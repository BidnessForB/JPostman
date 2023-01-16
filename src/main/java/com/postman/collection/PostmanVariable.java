package com.postman.collection;
import com.google.gson.Gson;
public class PostmanVariable implements IPostmanCollectionElement {
    private String key = "";
    private String value = "";
    
    
    public String getToken() {
        return "{{" + key + "}}";
    }

    public PostmanVariable(String key, String value) {
        this.key = key;
        this.value = value;
        
    }

    public String getKey() {
        return key;
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
    
    
}
