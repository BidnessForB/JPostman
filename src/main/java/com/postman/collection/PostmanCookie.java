package com.postman.collection;

import com.google.gson.Gson;

public class PostmanCookie implements IPostmanCollectionElement {
    private String domain = "";
    private String expires = "";
    private String maxAge = "";
    private boolean hostOnly;
    private boolean httpOnly;
    private String name = "";
    private String path = "";
    private boolean secure;
    private boolean session;
    private String value = "";
    private PostmanVariable[] extensions;


    
    public PostmanCookie() {
        
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String getExpires() {
        return expires;
    }
    public void setExpires(String expires) {
        this.expires = expires;
    }
    public String getMaxAge() {
        return maxAge;
    }
    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }
    public boolean isHostOnly() {
        return hostOnly;
    }
    public void setHostOnly(boolean hostOnly) {
        this.hostOnly = hostOnly;
    }
    public boolean isHttpOnly() {
        return httpOnly;
    }
    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public boolean isSecure() {
        return secure;
    }
    public void setSecure(boolean secure) {
        this.secure = secure;
    }
    public boolean isSession() {
        return session;
    }
    public void setSession(boolean session) {
        this.session = session;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public PostmanVariable[] getExtensions() {
        return extensions;
    }
    public void setExtensions(PostmanVariable[] extensions) {
        this.extensions = extensions;
    }
    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        return name;
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
