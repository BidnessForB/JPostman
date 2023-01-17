package com.postman.collection;

public interface IPostmanCollectionElement {
    public String getKey();
    
    public String toJson(boolean escaped, enumVariableResolution variableStrategy);
    public boolean isValid();
    
    
}