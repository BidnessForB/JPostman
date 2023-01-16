package com.postman.collection;

public class PostmanBody {
    private String mode;
    private PostmanBodyOptions options;
    private String raw;
    private PostmanGraphQL graphql;
    private PostmanVariable[] formdata;
    private PostmanVariable[] urlencoded;
    private PostmanBinaryFile file;
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
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
    public PostmanGraphQL getGraphql() {
        return graphql;
    }
    public void setGraphql(PostmanGraphQL graphql) {
        this.graphql = graphql;
    }
    public PostmanVariable[] getFormdata() {
        return formdata;
    }
    public void setFormdata(PostmanVariable[] formdata) {
        this.formdata = formdata;
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
    



}
