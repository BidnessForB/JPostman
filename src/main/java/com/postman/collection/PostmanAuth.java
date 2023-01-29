package com.postman.collection;

import java.util.HashMap;
    /** 
     * 
     * Class encapsulating the "auth" property of a PostmanCollectionItem.  Collections and requests can each have a single auth property,
     * which consists of a type (e.g., 'oauth1') and an array of parameters for that authentication type.  For example, oauth2 looks like:
     * <pre>
     * auth": {
        "type": "oauth2",
        "oauth2": [
            {
                "key": "grant_type",
                "value": "authorization_code",
                "type": "string"
            },
            {
                "key": "tokenName",
                "value": "Oauth2TokenName",
                "type": "string"
            },
            {
                "key": "tokenType",
                "value": "",
                "type": "string"
            },
            {
                "key": "accessToken",
                "value": "oauth2Token",
                "type": "string"
            },
            {
                "key": "addTokenTo",
                "value": "header",
                "type": "string"
            }
        ]
    }
    </pre>
     * Properties are stored as instances of PostmanVariable
     * 
     *
     */
public class PostmanAuth extends PostmanCollectionElement {

    private String type = "";
    public HashMap<String, PostmanVariable> properties = new HashMap<String, PostmanVariable>();
    private transient String[] arrTypes = new String[10];

    
    


    /** 
     * Default constructor.  Resulting auth has no type or any other properties initialized.
     * 
     * 
     */

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

    /** 
     * Conveninence constructor to initialize an Auth object with the specified type as String.  
     * 
     * @param type  The underlying type property of the new Auth object, as a String, e.g., "oauth1"
     */
    public PostmanAuth(String type) {
        this();
        this.type = type;
        this.setType(getType());
    }

       /** 
     * Conveninence constructor to initialize an Auth object with the specified type as an enumerated value.  
     * 
     * @param type  The underlying type property of the new Auth object, as an enumeration
     */
    public PostmanAuth(enumAuthType type) {
        this();
        this.setType(type);
    }
   /** 
     * Conveninence constructor to initialize an Auth object with a pre-created HashMap of authentication properties
     * 
     * @param type  The underlying type property of the new Auth object, as a String, e.g., "oauth1"
     * @param properties HashMap&#60;String, PostmanVariable&#62; containing the properties of this auth element
     */
    public PostmanAuth(enumAuthType type, HashMap<String, PostmanVariable> properties) {
        this(type);
        this.setType(type);
        this.setProperties(properties);

    }

/** 
     * Get the value of the type property of this authentication object.  The underlying string value is mapped to
     * an enumerated value.  
     * 
     * 
     * @return enumAuthType The underlying type of this authentication object, e.g., `oauth1`
     */
    public enumAuthType getType() {

        switch (type) {
            case "apikey", "bearer": {
                return enumAuthType.APIKEY;
            }
            case "digest": {
                return enumAuthType.DIGEST;
            }
            case "basic": {
                return enumAuthType.BASIC;
            }
            case "oauth1": {
                return enumAuthType.OAUTH1;
            }
            case "oauth2": {
                return enumAuthType.OAUTH2;
            }
            case "hawk": {
                return enumAuthType.HAWK;
            }
            case "ntlm": {
                return enumAuthType.NTLM;
            }
            case "edgegrid": {
                return enumAuthType.AKAMAI;
            }
            case "awsv4": {
                return enumAuthType.AWS;
            }
            default: {
                return null;
            }
        }
    }


    
    /** 
     * Returns the key of this PostmanCollectionElement for use in retrieving from arrays, etc.  
     * 
     * @return String
     */
    @Override
    public String getKey() {

        return type;
    }

    
    /** 
     * 
     * Set the underlying type property of this auth object using an enumerated value
     * 
     * @param type Enumerated value of the underlying type property
     */
    public void setType(enumAuthType type) {
        this.type = arrTypes[(type.ordinal())];
    }

    
    /** 
     * Set the properties of the Auth object using a pre-created HashMap&#60;String,PostmanVariable&#62; of properties.
     * 
     * @param properties
     */
    public void setProperties(HashMap<String, PostmanVariable> properties) {
        this.properties = properties;
    }

    
    /** 
     * Convenience method to return the type property of this auth object as a String
     * 
     * 
     * @return String  The 'type' property of this auth object, e.g., 'oauth1'
     */
    public String getAuthTypeAsString() {
        return arrTypes[this.getType().ordinal()];
    }

    
    /** 
     * 
     * Return the complete array of properties as a HashMap&#60;String,PostmanVariable&#62;, or null if none are set. 
     * 
     * 
     * @return HashMap&#60;String, PostmanVariable&#62;
     */
    public HashMap<String, PostmanVariable> getProperties() {

        return this.properties;
    }

    
    /** 
     * 
     * Add a new property, or replace an existing property
     * 
     * @param newElement The new property
     */
    public void addProperty(PostmanVariable newElement)  {
        if(this.properties == null) {
            this.properties = new HashMap<String, PostmanVariable>();
        }
        this.properties.put(newElement.getKey(), newElement);
    }

    
    /** 
     * Retrieve a single element from the array of authentication elements comprising this authentication object.  
     * 
     * @param key A string matching the key of the Auth element to return.  Returns null if the specified element is not present. 
     * @return PostmanVariable The auth element, or null if not found.
     */
    public PostmanVariable getProperty(String key) {
        return this.properties == null ? null : this.properties.get(key);

    }

    
    /** 
     * 
     * Convenience method create and then add a new property to this auth object
     * <p>
     * For example, to add the following JSON property to the array of properties:</p>
     * <pre>
     *  {
     *      "key": "realm",
     *	    "value": "testoauth@test.com",
	 *	    "type": "string"
     *  }
     * </pre>
     *
     * 
     * <code>authObj.setProperty("realm","somerealm@foo.com")</code>
     * 
     * Note that the type property always defaults to "string"
     * 
     * @param key  The key for the new property
     * @param value The value for the new property
     * 
     */
    public void addProperty(String key, String value)  {
        if(this.properties == null) {
            this.properties = new HashMap<String, PostmanVariable>();
        }
        this.properties.put(key, new PostmanVariable(key, value));

    }

    

}