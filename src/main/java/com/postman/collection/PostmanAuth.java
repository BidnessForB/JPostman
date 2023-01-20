package com.postman.collection;

import com.google.gson.*;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.Arrays;

////import java.util.UUID;


public class PostmanAuth implements IPostmanCollectionElement {
    ////private transient String key = UUID.randomUUID().toString();    
    private String type = "";
    
    private transient String[] arrTypes = new String[10];
    private transient HashMap<String, ArrayList<PostmanVariable>> elementArrays = new HashMap<String,  ArrayList<PostmanVariable>>();

    private transient HashMap<String, PostmanVariable> authElements = new HashMap<String, PostmanVariable>();
    
    
    public enumAuthType getType() {
        
        switch(type) {
            case "apikey", "bearer":
            {
                return enumAuthType.APIKEY;
            }
            case "digest":
            {
                return enumAuthType.DIGEST;
            }
            case "basic":
            {
                return enumAuthType.BASIC;
            }
            case "oauth1":
            {
                return enumAuthType.OAUTH1;
            }
            case "oauth2":
            {
                return enumAuthType.OAUTH2;
            }
            case "hawk":
            {
                return enumAuthType.HAWK;
            }
            case "ntlm":
            {
                return enumAuthType.NTLM;
            }
            case "edgegrid": 
            {
                return enumAuthType.AKAMAI;
            }
            case "awsv4": {
                return enumAuthType.AWS;
            }
            default:
            {
                return null;
            }
        }
    }

    public boolean validate() {
        return true;
    }

    public PostmanAuth() {
        arrTypes[enumAuthType.AKAMAI.ordinal()] = "edgegrid";
        arrTypes[enumAuthType.APIKEY.ordinal()] = "apikey";
        arrTypes[enumAuthType.AWS.ordinal()] = "awsv4";
        arrTypes[enumAuthType.BEARER.ordinal()] = "apikey";
        arrTypes[enumAuthType.BASIC.ordinal()] = "basic";
        arrTypes[enumAuthType.DIGEST.ordinal()] = "digest";
        arrTypes[enumAuthType.HAWK.ordinal()] = "hawk";
        arrTypes[enumAuthType.OAUTH1.ordinal()] = "oauth1";
        arrTypes[enumAuthType.OAUTH2.ordinal()] = "oauth2";
        arrTypes[enumAuthType.NTLM.ordinal()] = "ntlm";

    }
    
    @Override
    public String getKey() {
        
        return type;
    }


    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        
        return new Gson().toJson(this);
    }


    public void setType(enumAuthType authType) {
        this.type = arrTypes[(authType.ordinal())];
        }

    public PostmanVariable getAPIElement(String key) {
        return this.authElements.get(key);
     
    }

    public void setAuthElements(PostmanVariable[] newElements) {
        this.elementArrays.put(this.type, new ArrayList<PostmanVariable>(Arrays.asList(newElements))); 
    }


    

    public String getAuthTypeAsString() {
        return arrTypes[this.getType().ordinal()];
    }

    public ArrayList<PostmanVariable> getAuthElements(){
        return new ArrayList<PostmanVariable>(Arrays.asList(this.authElements.values().toArray(new PostmanVariable[0])));
    }
    
    
    public void setAuthElement(PostmanVariable newElement) throws Exception {
        this.authElements.put(newElement.getKey(), newElement);
       
    }

    public PostmanVariable getAuthElement(String key) {
        return this.authElements.get(key);
        
        
    }

    
    public void setAuthElement(String key, String value ) throws Exception {
        this.authElements.put(key, new PostmanVariable(key,value));
        
        
    }

    public PostmanAuth(enumAuthType type, PostmanVariable[] authElements) {
        
        this(type);
        this.setType(type);
        this.setAuthElements(authElements);
        
    }

    public PostmanAuth(enumAuthType type) {
        this();
        this.setType(type);
    }

}