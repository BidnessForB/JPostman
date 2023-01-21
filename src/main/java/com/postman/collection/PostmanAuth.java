package com.postman.collection;


import java.util.HashMap;






public class PostmanAuth extends PostmanCollectionElement {
    
    private String type = "";
    public HashMap<String, PostmanVariable> authElements = new HashMap<String, PostmanVariable>();
    private transient String[] arrTypes = new String[10];
    

    
    
    
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

    public PostmanAuth(String strType){
        this();
        this.type = strType;
        this.setType(getType());
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


   


    public void setType(enumAuthType authType) {
        this.type = arrTypes[(authType.ordinal())];
        }

    public PostmanVariable getAPIElement(String key) {
        return this.authElements.get(key);
     
    }

    public void setAuthElements(HashMap<String,PostmanVariable> newElements) {
        this.authElements = newElements;
    }


    

    public String getAuthTypeAsString() {
        return arrTypes[this.getType().ordinal()];
    }

    public HashMap<String,PostmanVariable> getAuthElements(){
        //return new ArrayList<PostmanVariable>(Arrays.asList(this.authElements.values().toArray(new PostmanVariable[0])));
        return this.authElements;
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

    public PostmanAuth(enumAuthType type, HashMap<String,PostmanVariable> authElements) {
        
        this(type);
        this.setType(type);
        this.setAuthElements(authElements);
        
    }

    public PostmanAuth(enumAuthType type) {
        this();
        this.setType(type);
    }

}