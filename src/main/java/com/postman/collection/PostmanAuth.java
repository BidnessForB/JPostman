package com.postman.collection;

import com.google.gson.*;

public class PostmanAuth implements IPostmanCollectionElement {
    
    private String type = "";
    private PostmanAPIKey[] apikey = null;
    
    public String getType() {
        return type;
    }

    
    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        return type;
    }


    @Override
    public void setKey(String key) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        // TODO Auto-generated method stub
        return new Gson().toJson(this);
    }


    public void setType(String type) {
        this.type = type;
    }

    public PostmanAPIKey[] getApikey() {
        return apikey;
    }

    public void setApikey(PostmanAPIKey[] apikey) {
        this.apikey = apikey;
    }

    public PostmanAuth(String type, PostmanAPIKey[] apikey) {
        this.type = type;
        this.apikey = apikey;
    }

}
