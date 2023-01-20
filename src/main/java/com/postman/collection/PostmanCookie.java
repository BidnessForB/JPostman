package com.postman.collection;

import com.google.gson.Gson;
import java.util.ArrayList;

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
    private ArrayList<PostmanVariable> extensions;


    
    public PostmanCookie() {
        
    }
    public String getDomain() {
        return domain;
    }

    public boolean isValid() {
        return true;
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
    public ArrayList<PostmanVariable> getExtensions() {
        return extensions;
    }
    public void setExtensions(ArrayList<PostmanVariable> extensions) {
        this.extensions = extensions;
    }
    @Override
    public String getKey() {
        
        return name;
    }
 
    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        
        return new Gson().toJson(this);
    }
    

    
}
