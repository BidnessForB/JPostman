package com.postman.collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class PostmanRequest  {
    private enumHTTPRequestMethod method = enumHTTPRequestMethod.GET;
    private PostmanUrl url;
    private PostmanHeader[] header = new PostmanHeader[0];
    
    private PostmanVariable[] variable;
    
    //private String description = "";
    
 

    public PostmanRequest(enumHTTPRequestMethod method, String host,String path) {
        
        this.setMethod(method);
        this.setUrl(new PostmanUrl(host, path));
        
        
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

    public void setVariables(PostmanVariable[] vars)
    {
        this.variable = vars;
    }

    public PostmanVariable[] getVariables() {
        return this.variable;
    }

    public void addVariable(PostmanVariable var)
    {
        List<PostmanVariable> liVars;
        
        if(this.variable != null)
        {
            liVars = new ArrayList<PostmanVariable>(Arrays.asList(this.variable));
        }
        else {
            liVars = new ArrayList<PostmanVariable>(Arrays.asList(new PostmanVariable[0]));
        }

        liVars.add(var);
        this.variable = liVars.toArray(new PostmanVariable[0]);

    }

}
