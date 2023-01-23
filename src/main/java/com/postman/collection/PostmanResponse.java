package com.postman.collection;

import java.util.ArrayList;
/**
 * 
 * Class to encapsulate the <code>response</code> object property of a PostmanItem
 * 
 * A PostmanRequest includes an optional array of expected responses: 
 * <pre>
 *  "response": [
                {
                    "name": "NORMAL Urlencoded",
                    "originalRequest": {
                        "method": "POST",
                        "url": {
                            "raw": "https://postman-echo.com/post",
                            "host": [
                                "postman-echo",
                                "com"
                            ],
                            "path": [
                                "post"
                            ],
                            "protocol": "https"
                        },
                        "body": {
                            "mode": "urlencoded",
                            "urlencoded": [
                                {
                                    "key": "x-field-2",
                                    "value": "value 2",
                                    "description": "This is value 2"
                                },
                                {
                                    "key": "x-field-1",
                                    "value": "value 1",
                                    "description": "This is value 1"
                                }
                            ]
                        }
                    },
                    "code": 200,
                    "status": "OK",
                    "_postman_previewlanguage": "",
                    "body": "this is the expected response body"
                }
            ]
            </pre>

The response object includes a PostmanRequest object describing the original request associated with this response.  

 * 
 * 
 */
public class PostmanResponse extends PostmanCollectionElement {
    private String name = "";
    private PostmanRequest originalRequest = null;
    private int code;
    private String status;
    private String _postman_previewlanguage = "";
    private ArrayList<PostmanCookie> cookie = null;
    private String body = "";
    private ArrayList<PostmanVariable> header;

    public PostmanResponse(String name, PostmanRequest originalRequest, int code, String status,
            String _postman_previewlanguage, ArrayList<PostmanCookie> cookie, String body) {
        this.name = name;
        this.originalRequest = originalRequest;
        this.code = code;
        this.status = status;
        this._postman_previewlanguage = _postman_previewlanguage;
        this.cookie = cookie;
        this.body = body;
    }

    
    /** 
     * Set the value of the <code>header</code> element array with a pre-populated ArrayList of PostmanVariable key-value pairs
     * 
     * 
     * @param headers
     */
    public void setHeader(ArrayList<PostmanVariable> headers) {
        this.header = headers;
    }

    
    /** 
     * Get the ArrayList containing the key-value paris in the <code>header</code> element array
     * 
     * @return ArrayList<PostmanVariable>
     */
    public ArrayList<PostmanVariable> getHeader() {
        return this.header;
    }

    public PostmanResponse(String name, PostmanRequest req, String status, int code, String body) {
        this.originalRequest = req;
        this.status = status;
        this.code = code;
        this.body = body;
        this.name = name;
    }

    
    /** 
     * 
     * Get the name of this response.
     * 
     * @return String The name
     */
    public String getName() {
        return name;
    }

    
    /** 
     * Set the name of this response
     * 
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * 
     * Get the value of the <code>originalRequest</code> for this response as a PostmanRequest, or null if it has not been set.
     * 
     * @return PostmanRequest
     */
    public PostmanRequest getOriginalRequest() {
        return originalRequest;
    }

    
    /** 
     * 
     * Set the value of the <code>originalRequest</code> property
     * 
     * @param originalRequest
     */
    public void setOriginalRequest(PostmanRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    
    /** 
     * 
     * Get the value of the <code>code</code> property, the HTML status code associated with this response.
     * @return int
     */
    public int getCode() {
        return code;
    }

    
    /** 
     * Set the value of the <code>code</code> property, the HTML status code associated with this response.
     * 
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    
    /** 
     * 
     * Get the value of the <code>status</code> property for this response, the string associated with the <code>code></code> property, eg. "OK"
     * 
     * @return String The status as a string, eg. "OK"
     */
    public String getStatus() {
        return status;
    }

    
    /** 
     * 
     * Set the value of the <code>status</code> property for this response, the string associated with the <code>code></code> property, eg. "OK"
     * 
     * @param status The status. Not validated against existing http response codes
     */
    public void setStatus(String status) {
        this.status = status;
    }

    
    /** 
     * 
     * Get the value of the <code>_postman_previewlanguage</code> property 
     * @return String The language
     */
    public String getPostmanPreviewlanguage() {
        return _postman_previewlanguage;
    }

    
    /** 
     * 
     * 
     * Set the value of the <code>_postman_previewlanguage</code> property 
     * 
     * @param PostmanPreviewlanguage The language
     */
    public void SetPostmanPreviewlanguage(String PostmanPreviewlanguage) {
        this._postman_previewlanguage = _postman_previewlanguage;
    }

    
    /** 
     * 
     * Return an ArrayList containing the PostmanCookie objects comprising the value of the <code>cookie</code> array property
     * 
     * @return ArrayList<PostmanCookie> The cookies
     */
    public ArrayList<PostmanCookie> getCookies() {
        return cookie;
    }

    
    /** 
     * 
     * Set the value of the <code>cookie</code> array property with a pre-populated ArrayList
     * 
     * @param cookie
     */
    public void setCookies(ArrayList<PostmanCookie> cookie) {
        this.cookie = cookie;
    }

    
    /** 
     * 
     * Get the value of the <code>body</code> property of the response, or null if not set.
     * 
     * @return String The body
     */
    public String getBody() {
        return body;
    }

    
    /** 
     * Set the value of the <code>body</code> property of the response
     * 
     * @param body  The body.
     */

     //TODO: Ensure string is properly escaped? 
    public void setBody(String body) {
        this.body = body;
    }

    
    /** 
     * 
     * Return the Key for this object.  Not currently implemented
     * 
     * @return String  The key
     */
    @Override
    public String getKey() {

        return null;
    }

}
