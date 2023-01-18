package com.postman.collection;
import java.util.UUID;
public class PostmanBodyOptions {
    private PostmanBodyRaw raw;
    private transient String key = UUID.randomUUID().toString();
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
