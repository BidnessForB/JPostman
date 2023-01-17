package com.postman.collection;
import java.util.UUID;

public class PostmanBinaryFile {
    private String src;
    private transient String key = UUID.randomUUID().toString();

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public PostmanBinaryFile(String src) {
        this.src = src;
    }   
}
