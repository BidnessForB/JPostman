package com.postman.collection;

public interface IPostmanCollectionElement {
    public String getKey();
    public void setKey(String key);
    public String toJson(boolean escaped, enumVariableResolution variableStrategy);
    
    
}
