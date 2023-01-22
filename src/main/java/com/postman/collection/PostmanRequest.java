package com.postman.collection;

import java.util.ArrayList;

public class PostmanRequest extends PostmanCollectionElement {
    private enumHTTPRequestMethod method = enumHTTPRequestMethod.GET;
    private PostmanUrl url;
    private ArrayList<PostmanVariable> header;// = new PostmanVariable[0];
    private String description;
    private PostmanBody body;
    private PostmanAuth auth;

    
    /** 
     * @return String
     */
    public String getKey() {
        return null;
    }

    public PostmanRequest(enumHTTPRequestMethod method, String host, String path) {

        this.setMethod(method);
        this.setUrl(new PostmanUrl(host, path));

    }

    public PostmanRequest(enumHTTPRequestMethod method, PostmanUrl url) {

        this.setMethod(method);
        this.setUrl(url);

    }

    
    /** 
     * @param auth
     */
    public void setAuth(PostmanAuth auth) {
        this.auth = auth;
    }

    
    /** 
     * @return PostmanAuth
     */
    public PostmanAuth getAuth() {
        return this.auth;
    }

    public PostmanRequest(enumHTTPRequestMethod method, String URL) throws Exception {

        this.setUrl(new PostmanUrl(URL));
        this.setMethod(method);

    }

    
    /** 
     * @return enumHTTPRequestMethod
     */
    public enumHTTPRequestMethod getMethod() {
        return method;
    }

    
    /** 
     * @param method
     */
    public void setMethod(enumHTTPRequestMethod method) {
        this.method = method;
    }

    
    /** 
     * @return PostmanUrl
     */
    public PostmanUrl getUrl() {
        return url;
    }

    
    /** 
     * @param url
     */
    public void setUrl(PostmanUrl url) {
        this.url = url;
    }

    
    /** 
     * @return ArrayList<PostmanVariable>
     */
    public ArrayList<PostmanVariable> getHeader() {
        return header;
    }

    
    /** 
     * @param header
     */
    public void setHeader(ArrayList<PostmanVariable> header) {
        this.header = header;
    }

    
    /** 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    
    /** 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /** 
     * @param bodyMode
     * @return PostmanBody
     */
    public PostmanBody setBody(enumRequestBodyMode bodyMode) {
        return this.setBody(bodyMode, null);
    }

    
    /** 
     * @param bodyMode
     * @param rawContent
     * @return PostmanBody
     */
    public PostmanBody setBody(enumRequestBodyMode bodyMode, String rawContent) {
        this.setBody(new PostmanBody(bodyMode, rawContent, enumRawBodyLanguage.TEXT));
        return this.getBody();
    }

    
    /** 
     * @return PostmanBody
     */
    public PostmanBody getBody() {
        return this.body;
    }

    
    /** 
     * @param body
     */
    public void setBody(PostmanBody body) {
        this.body = body;
    }

}
