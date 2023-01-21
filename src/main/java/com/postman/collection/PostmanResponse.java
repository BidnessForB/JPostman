package com.postman.collection;

import java.util.ArrayList;

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
     * @param headers
     */
    public void setHeader(ArrayList<PostmanVariable> headers) {
        this.header = headers;
    }

    
    /** 
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
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return PostmanRequest
     */
    public PostmanRequest getOriginalRequest() {
        return originalRequest;
    }

    
    /** 
     * @param originalRequest
     */
    public void setOriginalRequest(PostmanRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    
    /** 
     * @return int
     */
    public int getCode() {
        return code;
    }

    
    /** 
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    
    /** 
     * @return String
     */
    public String getStatus() {
        return status;
    }

    
    /** 
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    
    /** 
     * @return String
     */
    public String get_postman_previewlanguage() {
        return _postman_previewlanguage;
    }

    
    /** 
     * @param _postman_previewlanguage
     */
    public void set_postman_previewlanguage(String _postman_previewlanguage) {
        this._postman_previewlanguage = _postman_previewlanguage;
    }

    
    /** 
     * @return ArrayList<PostmanCookie>
     */
    public ArrayList<PostmanCookie> getCookies() {
        return cookie;
    }

    
    /** 
     * @param cookie
     */
    public void setCookies(ArrayList<PostmanCookie> cookie) {
        this.cookie = cookie;
    }

    
    /** 
     * @return String
     */
    public String getBody() {
        return body;
    }

    
    /** 
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }

    
    /** 
     * @return String
     */
    @Override
    public String getKey() {

        return null;
    }

}
