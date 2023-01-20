package com.postman.collection;
import com.google.gson.Gson;
import java.util.ArrayList;
public class PostmanResponse implements IPostmanCollectionElement {
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

    public boolean isValid() {
        return true;
    }

    public void  setHeader(ArrayList<PostmanVariable> headers) {
        this.header = headers;
    }

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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PostmanRequest getOriginalRequest() {
        return originalRequest;
    }

    public void setOriginalRequest(PostmanRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String get_postman_previewlanguage() {
        return _postman_previewlanguage;
    }

    public void set_postman_previewlanguage(String _postman_previewlanguage) {
        this._postman_previewlanguage = _postman_previewlanguage;
    }

    public ArrayList<PostmanCookie> getCookies() {
        return cookie;
    }

    public void setCookies(ArrayList<PostmanCookie> cookie) {
        this.cookie = cookie;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String getKey() {
        
        return null;
    }

    

    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        
        return new Gson().toJson(this);
    }

    

}
