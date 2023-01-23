package com.postman.collection;

import java.util.ArrayList;
/**
 * 
 * 
 * Encapsulates the <code>request</code> object property of a PostmanItem object
 * 
 * <pre>{@code 
* {
    "name": "URL 7",
    "request": {
        "method": "GET",
        "header": [],
        "url": {
            "raw": "{{baseUrl}}foo.com/bar/:path1/bat.json?foo&#61;1&bar&#61;",
            "host": [
                "{{baseUrl}}foo",
                "com"
            ],
            "path": [
                "bar",
                ":path1",
                "bat.json"
            ],
            "query": [
                {
                    "key": "foo",
                    "value": "1"
                },
                {
                    "key": "bar",
                    "value": ""
                }
            ],
            "variable": [
                {
                    "key": "path1",
                    "value": "path-value"
                }
            ]
        }
    },
    "response": []
}
}</pre> * 
 * 
 */
public class PostmanRequest extends PostmanCollectionElement {
    private enumHTTPRequestMethod method = enumHTTPRequestMethod.GET;
    private PostmanUrl url;
    private ArrayList<PostmanVariable> header;// = new PostmanVariable[0];
    private String description;
    private PostmanBody body;
    private PostmanAuth auth;
    private String name;

    
    /** 
     * 
     * Returns the value of the <code>name</code> property
     * 
     * @return String
     */
    public String getKey() {
        return this.name;
    }

    /**
     * Construct a PostmanRequest with the specified HTTP method, host and path.  All URL components are constructed from the host and path strings.
     * 
     * @param method Enumerated value for the HTTP method
     * @param host  String containing the host portion of the URL.
     * @param path String containing the path portion of the URL.
     */
    public PostmanRequest(enumHTTPRequestMethod method, String host, String path) {

        this.setMethod(method);
        this.setUrl(new PostmanUrl(host, path));

    }


     /**
     * Construct a PostmanRequest with the specified HTTP method, and PostmanUrl. 
     * 
     * @param method Enumerated value for the HTTP method
     * @param url  Pre-constructed PostmanUrl object
     * 
     */
    public PostmanRequest(enumHTTPRequestMethod method, PostmanUrl url) {

        this.setMethod(method);
        this.setUrl(url);
    }

    
    /** 
     * 
     * Set the values in the <code>auth</code> array with a pre-populated PostmanAuth object.
     * 
     * @param auth  The auth
     */
    public void setAuth(PostmanAuth auth) {
        this.auth = auth;
    }

    
    /** 
     * 
     * Get the PostmanAuth object containing the values of the <code>auth</code> array, or null if it has not been set.
     * 
     * @return PostmanAuth The auth object containing the values.
     */
    public PostmanAuth getAuth() {
        return this.auth;
    }


    /**
     * 
     * Construct a PostmanRequest object from a raw URL.  All path, host, URL, auth and other property array elements are parsed out and populated
     * 
     * 
     * @param method  Enumerated value for the HTTP method
     * @param URL  The raw URL 
     * @throws Exception
     */
    public PostmanRequest(enumHTTPRequestMethod method, String URL)  {

        this.setUrl(new PostmanUrl(URL));
        this.setMethod(method);

    }

    
    /** 
     * 
     * Return the enumerated value of the <code>method</code> property
     * 
     * @return enumHTTPRequestMethod
     */
    public enumHTTPRequestMethod getMethod() {
        return method;
    }

    
    /** 
     * 
     * Set the value of the <code>method</code> property
     * 
     * @param method
     */
    public void setMethod(enumHTTPRequestMethod method) {
        this.method = method;
    }

    
    /** 
     * 
     * Return the PostmanUrl containing the values of the <code>url</code> property array
     * 
     * @return PostmanUrl
     */
    public PostmanUrl getUrl() {
        return url;
    }

    
    /** 
     * 
     * Set the values of the <code>url</code> property 
     * 
     * @param url  PostmanUrl object containing the values
     */
    public void setUrl(PostmanUrl url) {
        this.url = url;
    }

    
    /** 
     * 
     * Return an ArrayList of PostmanVariable objects containing the key-value pair values for the <code>header</code> property array
     * 
     * 
     * @return ArrayList<PostmanVariable>  The headers
     */
    public ArrayList<PostmanVariable> getHeader() {
        return header;
    }

    
    /** 
     * 
     * Set the key-value pair values for the <code>header</code> property array
     * 
     * @param header  Header values
     */
    public void setHeader(ArrayList<PostmanVariable> header) {
        this.header = header;
    }

    
    /** 
     * 
     * Get the value of the <code>description</code> property, generally the documentation for the request
     * 
     * @return String The description
     */
    public String getDescription() {
        return description;
    }

    
    /** 
     * Set the value of the <code>description</code> property, generally the documentation for the request
     * 
     * 
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /** 
     * 
     * Populate the <code>body</code> array with an empty <code>body</code> property object with the specified <code>mode</code> value.  
     * 
     * @param bodyMode The body mode, eg. RAW
     * @return PostmanBody  The new body
     */
    public PostmanBody setBody(enumRequestBodyMode bodyMode) {
        return this.setBody(bodyMode, null);
    }

    
    /** 
     * 
     * 
     * Populate the <code>body</code> array with the specified body content.  The <code>language</code> property of the 
     * request will best set to "text"
     * 
     * 
     * @param bodyMode  Enumerated value for the <code>mode</code> property
     * @param rawContent The raw content for the body
     * @return PostmanBody The new body
     */
    public PostmanBody setBody(enumRequestBodyMode bodyMode, String rawContent) {
        this.setBody(new PostmanBody(bodyMode, rawContent, enumRawBodyLanguage.TEXT));
        return this.getBody();
    }

    
    /** 
     * Return the PostmanBody object containing the values in the <code>body</code> property object, or null if it has not been set.
     * 
     * @return PostmanBody The body, or null.  
     */
    public PostmanBody getBody() {
        return this.body;
    }

    
    /** 
     * 
     * Set the value of the <code>body</code> property object
     * 
     * @param body The new body values
     */
    public void setBody(PostmanBody body) {
        this.body = body;
    }

}
