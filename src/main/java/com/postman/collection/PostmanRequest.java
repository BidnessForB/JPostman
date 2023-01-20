package com.postman.collection;
import java.util.ArrayList;
public class PostmanRequest  {
    private enumHTTPRequestMethod method = enumHTTPRequestMethod.GET;
    private PostmanUrl url;
    private ArrayList<PostmanVariable> header;// = new PostmanVariable[0];
    private String description;
    private PostmanBody body;
    private PostmanAuth auth;
    
    

    //private String description = "";
    
 

    public PostmanRequest(enumHTTPRequestMethod method, String host,String path) {
        
        this.setMethod(method);
        this.setUrl(new PostmanUrl(host, path));
        
        
    }

    public PostmanRequest(enumHTTPRequestMethod method, PostmanUrl url) {
        
        this.setMethod(method);
        this.setUrl(url);
        
    }

    public void setAuth(PostmanAuth auth) {
        this.auth = auth;
    }

    public PostmanAuth getAuth() {
        return this.auth;
    }

    public PostmanRequest(enumHTTPRequestMethod method, String URL) throws Exception {
        
        this.setUrl(new PostmanUrl(URL));
        this.setMethod(method);
        
    }

    

    public enumHTTPRequestMethod getMethod() {
        return method;
    }

    public boolean validate() throws Exception {
        boolean valid = true;
        valid = valid && (method != null);
        valid = valid && (url.validate() && url != null);
        
        return valid;
    }


    public void setMethod(enumHTTPRequestMethod method) {
        this.method = method;
    }

    public PostmanUrl getUrl() {
        return url;
    }

    public void setUrl(PostmanUrl url) {
        this.url = url;
    }

    public ArrayList<PostmanVariable> getHeader() {
        return header;
    }

    public void setHeader(ArrayList<PostmanVariable> header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PostmanBody setBody(enumRequestBodyMode bodyMode) {
        return this.setBody(bodyMode, null);
    }

    public PostmanBody setBody(enumRequestBodyMode bodyMode, String rawContent) {
        this.setBody(new PostmanBody(bodyMode, rawContent, enumRawBodyLanguage.TEXT));
        return this.getBody();
    }
    
    public PostmanBody getBody() {
        return this.body;
    }

    public void setBody(PostmanBody body) {
        this.body = body;
    }

    

}
