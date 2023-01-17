package com.postman.collection;

public class PostmanBodyOptions {
    private PostmanBodyRaw raw;

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
