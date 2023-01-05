package com.postman.collection;
import com.google.gson.Gson;
public class PostmanVariable implements IPostmanCollectionElement {
    private String key = "";
    private String value = "";
    private String type = "";
    
    public String getToken() {
        return "{{" + key + "}}";
    }

    public PostmanVariable(String key, String value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        // TODO Auto-generated method stub
        return new Gson().toJson(this);
    }
    
    
}
