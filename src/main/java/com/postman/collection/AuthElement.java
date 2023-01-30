package com.postman.collection;


    /** 
     * 
     * Class encapsulating the "auth" property of a CollectionItem.  Collections and requests can each have a single auth property,
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
public class AuthElement extends CollectionElement {

    private String type = "";
    private VariableListMap<PostmanVariable> properties = null;
    private transient String[] arrTypes = new String[10];

    
    


    /** 
     * Default constructor.  Resulting auth has no type or any other properties initialized.
     * 
     * 
     */

    public AuthElement() {
        arrTypes[enumAuthType.AKAMAI.ordinal()] = "edgegrid";
        arrTypes[enumAuthType.APIKEY.ordinal()] = "apikey";
        arrTypes[enumAuthType.AWS.ordinal()] = "awsv4";
        arrTypes[enumAuthType.BEARER.ordinal()] = "bearer";
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
    public AuthElement(String type) {
        this();
        this.type = type;
        this.setAuthType(getAuthType());
    }

       /** 
     * Conveninence constructor to initialize an Auth object with the specified type as an enumerated value.  
     * 
     * @param type  The underlying type property of the new Auth object, as an enumeration
     */
    public AuthElement(enumAuthType type) {
        this();
        this.setAuthType(type);
    }
   /** 
     * Conveninence constructor to initialize an Auth object with a pre-created HashMap of authentication properties
     * 
     * @param type  The underlying type property of the new Auth object, as a String, e.g., "oauth1"
     * @param properties HashMap&#60;String, PostmanVariable&#62; containing the properties of this auth element
     */
    public AuthElement(enumAuthType type, VariableListMap<PostmanVariable> properties) {
        this(type);
        this.setAuthType(type);
        this.setProperties(properties);

    }



    
    /** 
     * Returns the key of this CollectionElement for use in retrieving from arrays, etc.  
     * 
     * @return String
     */
    @Override
    public String getKey() {

        return type;
    }

/** 
     * Get the value of the type property of this authentication object.  The underlying string value is mapped to
     * an enumerated value.  
     * 
     * 
     * @return enumAuthType The underlying type of this authentication object, e.g., `oauth1`
     */
    public enumAuthType getAuthType() {

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
     * 
     * Set the underlying type property of this auth object using an enumerated value.  If the type was previously set to a different value, the properties collection
     * is set to null.
     * 
     * @param type Enumerated value of the underlying type property
     */
    public void setAuthType(enumAuthType type) {
        this.type = arrTypes[(type.ordinal())];
        VariableListMap<PostmanVariable> newProps = new VariableListMap<PostmanVariable>();
        switch (type) {
            case AKAMAI: {
                newProps.add(new PostmanVariable("headersToSign", null));
                newProps.add(new PostmanVariable("baseURL",null));
                newProps.add(new PostmanVariable("timestamp",null));
                newProps.add(new PostmanVariable("nonce",null));
                newProps.add(new PostmanVariable("clientSecret",null));
                newProps.add(new PostmanVariable("clientToken",null));
                newProps.add(new PostmanVariable("accessToken",null));
                break;
            }
            case APIKEY: {
                newProps.add(new PostmanVariable("key",null));
                newProps.add(new PostmanVariable("value",null));
                newProps.add(new PostmanVariable("in",null));
                break;
            }
            case AWS: { 
                newProps.add(new PostmanVariable("sessionToken", null));
                newProps.add(new PostmanVariable("service", null));
                newProps.add(new PostmanVariable("secretKey", null));
                newProps.add(new PostmanVariable("accessKey", null));
                newProps.add(new PostmanVariable("addAuthDataToQuery", null));
                break;
            }
            case BEARER:{
                newProps.add(new PostmanVariable("key","token"));
                newProps.add(new PostmanVariable("value",null));
                newProps.add(new PostmanVariable("type","string"));
                break;
            }
            case BASIC: {
                newProps.add(new PostmanVariable("password",null));
                newProps.add(new PostmanVariable("username",null));
                break;
            }
            case DIGEST: {
                newProps.add(new PostmanVariable("opaque",null));
                newProps.add(new PostmanVariable("clientNonce",null));
                newProps.add(new PostmanVariable("nonceCount",null));
                newProps.add(new PostmanVariable("qop",null));
                newProps.add(new PostmanVariable("algorithim",null));
                newProps.add(new PostmanVariable("nonce",null));
                newProps.add(new PostmanVariable("realm",null));
                newProps.add(new PostmanVariable("password",null));
                break;
            }

            case HAWK: {
                newProps.add(new PostmanVariable("includePayloadHash",null));
                newProps.add(new PostmanVariable("timestamp",null));
                newProps.add(new PostmanVariable("delegation",null));
                newProps.add(new PostmanVariable("app",null));
                newProps.add(new PostmanVariable("extraData",null));
                newProps.add(new PostmanVariable("nonce",null));
                newProps.add(new PostmanVariable("user",null));
                newProps.add(new PostmanVariable("authKey",null));
                newProps.add(new PostmanVariable("algorithim",null));
                break;
            }

            case OAUTH1: {
                newProps.add(new PostmanVariable("addEmptyParamsToSign", null, null, "boolean"));
                newProps.add(new PostmanVariable("includeBodyHash", "true", null, "boolean"));
                newProps.add(new PostmanVariable("realm", null));
                newProps.add(new PostmanVariable("nonce",null));
                newProps.add(new PostmanVariable("timestamp",null));
                newProps.add(new PostmanVariable("verifier",null));
                newProps.add(new PostmanVariable("callback",null));
                newProps.add(new PostmanVariable("tokenSecret",null));
                newProps.add(new PostmanVariable("token",null));
                newProps.add(new PostmanVariable("consumerSecret",null));
                newProps.add(new PostmanVariable("consumerKey",null));
                newProps.add(new PostmanVariable("signatureMethod",null));
                newProps.add(new PostmanVariable("version",null));
                newProps.add(new PostmanVariable("addParamsToHeader", "false", null, "boolean"));
                break;
            }
            case OAUTH2: {
                newProps.add(new PostmanVariable("grant_type",null));
                newProps.add(new PostmanVariable("tokenName",null));
                newProps.add(new PostmanVariable("tokenType",null));
                newProps.add(new PostmanVariable("accessToken", null));
                newProps.add(new PostmanVariable("addTokenTo", null));
                break;
            }
            case NTLM: {
                newProps.add(new PostmanVariable("workstation",null));
                newProps.add(new PostmanVariable("domain", null));
                newProps.add(new PostmanVariable("password", null));
                newProps.add(new PostmanVariable("username", null));
                break;
            }
            default: {
                newProps = null;
            }
        }
        


        this.properties = newProps;
    }

     /** 
     * Convenience method to return the type property of this auth object as a String
     * 
     * 
     * @return String  The 'type' property of this auth object, e.g., 'oauth1'
     */
    public String getAuthTypeAsString() {
        return arrTypes[this.getAuthType().ordinal()];
    }

    /** 
     * Set the properties of the Auth object using a pre-created HashMap&#60;String,PostmanVariable&#62; of properties.
     * 
     * @param properties
     */
    public void setProperties(VariableListMap<PostmanVariable> properties) {
        this.properties = properties;
    }

    
   
    
    /** 
     * 
     * Return the complete array of properties as a HashMap&#60;String,PostmanVariable&#62;, or null if none are set. 
     * 
     * 
     * @return HashMap&#60;String, PostmanVariable&#62;
     */
    public VariableListMap<PostmanVariable> getProperties() {

        return this.properties;
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
     * Add a new property, or replace an existing property
     * 
     * @param newElement The new property
     */
    public void addProperty(PostmanVariable newElement)  {
        if(this.properties == null) {
            this.properties = new VariableListMap<PostmanVariable>();
        }
        this.properties.add(newElement);
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
            this.properties = new VariableListMap<PostmanVariable>();
        }
        this.addProperty(new PostmanVariable(key, value));

    }

    

    

}