package com.postman.collection;


public class PostmanRequest extends PostmanItem  {
    private enumHTTPRequestMethod method = enumHTTPRequestMethod.GET;
    private PostmanUrl url;
    private PostmanHeader[] header = new PostmanHeader[0];
    
    //private String description = "";
    
 

    public PostmanRequest(enumHTTPRequestMethod method, String host,String path, String name, String description) {
        this.setMethod(method);
        this.setUrl(new PostmanUrl(host, path));
        this.setName(name);
        this.setDescription(description);
    }

    public PostmanRequest(enumHTTPRequestMethod method, PostmanUrl url) {
        this.setMethod(method);
        this.setUrl(url);
    }

    public PostmanRequest(enumHTTPRequestMethod method, String URL) {
        this.setUrl(new PostmanUrl(URL));
        this.setMethod(method);
    }

    

    public enumHTTPRequestMethod getMethod() {
        return method;
    }

    public boolean isValid() {
        boolean valid = true;
        valid = valid && (method != null);
        valid = valid && (url.isValid() && url != null);
        valid = this.getName() != null;
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

    public PostmanHeader[] getHeader() {
        return header;
    }

    public void setHeader(PostmanHeader[] header) {
        this.header = header;
    }

  

    
    
    
    public void addItem(PostmanItem item) throws Exception {
        throw new Exception("Cannot add items to a Request");
    } 

    public void removeItem(PostmanItem item) throws Exception {
        throw new Exception("Cannot remove an item from a Request");
    }
}
