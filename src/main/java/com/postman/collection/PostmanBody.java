package com.postman.collection;

//import java.util.UUID;

import com.postman.collection.util.CollectionUtils;

public class PostmanBody {
    private String mode;
    private PostmanBodyOptions options;
    private String raw;
    private PostmanGraphQL graphql;
    private PostmanVariable[] formdata;
    private PostmanVariable[] urlencoded;
    private PostmanBinaryFile file;
    //private transient String key = UUID.randomUUID().toString();
    


    
    public PostmanBodyOptions getOptions() {
        return options;
    }
    public void setOptions(PostmanBodyOptions options) {
        this.options = options;
    }
    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public void setRaw(String raw, enumRawBodyLanguage language) {
        
        
        if(this.getMode() == enumRequestBodyMode.TEXT)
        {
            this.raw = raw;
            this.options = null;
            return;
        }
        if(this.getMode() == enumRequestBodyMode.RAW)
        {
            this.options = new PostmanBodyOptions(new PostmanBodyRaw(language));
            this.raw = raw;
        }
        if(this.getMode() == enumRequestBodyMode.FORMDATA || this.getMode() == enumRequestBodyMode.URLENCODED)
        {
            this.options = null;
        }
        if(this.getMode() == enumRequestBodyMode.GRAPHQL) {
            this.options = null;
            this.graphql = new PostmanGraphQL(raw);
        }

        
    }

    public PostmanGraphQL getGraphql() {
        return graphql;
    }
    public void setGraphql(PostmanGraphQL graphql) {
        this.graphql = graphql;
    }

    public void setGraphql(String graphQL) {
        this.setGraphql(new PostmanGraphQL(graphQL));
    }

    public void setGraphql(String graphQL, String variables) {
        this.setGraphql(new PostmanGraphQL(graphQL, variables));
    }

    

    public PostmanVariable[] getFormdata() throws Exception {
        return formdata;
    }

    public PostmanVariable getFormdata(int position) throws Exception {
        switch(this.getMode())
        {
            case URLENCODED:
            {
                return this.urlencoded[position];
            }
            case FORMDATA:
            {
                return this.formdata[position];
            }
            default:
            {
                throw new Exception ("Body mode " + this.getMode() + ". Must be URLENCODED or FORMDATA to add form data");
            }
        }
    }

    public void setFormdata(PostmanVariable[] formdata) {
        this.formdata = formdata;
    }
    
    public void setFormdata(String key, String value, String description, int position) throws Exception {
        this.setFormdata(new PostmanVariable(key, value, description, "text"), position);
    }

    public void setFormdata(String key, String value, String description) throws Exception {
        this.setFormdata(new PostmanVariable(key, value, description), 0);
    }

    public void setFormdata(PostmanVariable data, int position) throws Exception {
        
        

        if(this.getMode() == enumRequestBodyMode.URLENCODED)  {
            this.urlencoded = CollectionUtils.insertInCopy(this.urlencoded, data, position);
        }
        else if(this.getMode() == enumRequestBodyMode.FORMDATA)
        {
            this.formdata = CollectionUtils.insertInCopy(this.formdata, data, position);
        }
        else {
            throw new Exception("Cannot set Form Data.  Mode must be URLENCODED or FORMDATA");
        }
    }


    
    public PostmanBody(enumRequestBodyMode mode, String content, enumRawBodyLanguage language) {
        
        this.setMode(mode);

        if(this.getMode() == enumRequestBodyMode.RAW || this.getMode() == enumRequestBodyMode.GRAPHQL)
        {
            this.setRaw(content, language);
        }
        if(this.getMode() == enumRequestBodyMode.FILE)
        {
            this.file.setSrc(content);
        }
        
        
    }

    public PostmanVariable[] getUrlencoded() {
        return urlencoded;
    }
    public void setUrlencoded(PostmanVariable[] urlencoded) {
        this.urlencoded = urlencoded;
    }
    public PostmanBinaryFile getFile() {
        return file;
    }
    public void setFile(PostmanBinaryFile file) {
        this.file = file;
    }
    public enumRequestBodyMode getMode() {
        if(mode == null)
        {
            return null;
        }
        switch(mode) {
            case "file":
                return enumRequestBodyMode.FILE;
            case "formdata":
                return enumRequestBodyMode.FORMDATA;
            case "urlencoded":
                return enumRequestBodyMode.URLENCODED;
            case "graphql":
                return enumRequestBodyMode.GRAPHQL;
            case "raw":
                return enumRequestBodyMode.RAW;
        }
        return null;
    }
    public void setMode(enumRequestBodyMode newMode) {
        switch(newMode)
         {
        case FILE:
          this.mode = "file";
          this.file = new PostmanBinaryFile("");
          this.formdata = null;
          this.urlencoded = null;
          this.graphql = null;
          this.options = null;
          
          break;
        case FORMDATA:
        this.mode = "formdata";
        this.file = null;
        this.formdata = new PostmanVariable[0];;
        this.urlencoded = null;
        this.graphql = null;
        this.options = null;
          break;
        case URLENCODED:
        this.mode = "urlencoded";
        this.file = null;
        this.formdata = null;
        this.urlencoded = new PostmanVariable[0];
        this.graphql = null;
        this.options = null;
        break;
        case GRAPHQL:
          this.mode = "graphql";
          this.file = null;
        this.formdata = null;
        this.urlencoded = null;
        this.graphql = null;
        this.options = null;
          break;
        case RAW:
          this.mode = "raw";
          this.file = null;
        this.formdata = null;
        this.urlencoded = null;
        this.graphql = null;
        this.options = null;
          break;
        case TEXT:
          this.mode = "raw";
          this.file = null;
        this.formdata = null;
        this.urlencoded = null;
        this.graphql = null;
        this.options = null;
        }
    }

    
    public void removeFormData(int position) {

    }

    public void removeFormDAta(PostmanVariable data) {

    }

    
    public PostmanBody(enumRequestBodyMode mode) {
        this.setMode(mode);
    }

class PostmanBodyOptions {
    private PostmanBodyRaw raw;
    //private transient String key = UUID.randomUUID().toString();
    public PostmanBodyRaw getRaw() {
        return raw;
    }

    public void setRaw(PostmanBodyRaw raw) {
        this.raw = raw;
    }

    public PostmanBodyOptions(PostmanBodyRaw raw) {
        this.raw = raw;
    }

    public PostmanBodyOptions(enumRawBodyLanguage language) {
        this.raw = new PostmanBodyRaw(language);
    }
}



}