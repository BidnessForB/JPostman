package com.postman.collection;

import com.google.gson.Gson;

public class PostmanHeader implements IPostmanCollectionElement {
    private String key = "";
    private String value = "";


    
    public PostmanHeader(String key, String value) {
        this.key = key;
        this.value = value;
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

    public boolean isValid() {
        return true;
    }

    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        
        return new Gson().toJson(this);
    }
    
}
