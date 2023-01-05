package com.postman.collection;
import com.google.gson.Gson;
public class PostmanRequest implements IPostmanCollectionElement {
    private String method = "";
    private PostmanUrl url;
    private PostmanHeader[] header;
    private String description = "";
    
    public PostmanRequest(String method, PostmanUrl url, PostmanHeader[] header, String description) {
        this.method = method;
        this.url = url;
        this.header = header;
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public PostmanUrl getUrl() {
        return url;
    }

    public void setUrl(PostmanUrl url) {
        this.url = url;
    }

    public PostmanHeader[] getHeader() {
        return header;
    }

    public void setHeader(PostmanHeader[] header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getKey() {
        // TODO Auto-generated method stub
       return null;
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




}
