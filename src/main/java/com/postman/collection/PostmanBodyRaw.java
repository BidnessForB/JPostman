package com.postman.collection;
//import java.util.UUID;
public class PostmanBodyRaw {
    private String language;
    //private transient String key = UUID.randomUUID().toString();
    public enumRawBodyLanguage getLanguage() {
        if(language == null)
        {
            return null;
        }
        switch(language) {
            case "javascript":
                return enumRawBodyLanguage.JAVASCRIPT;
            case "json":
                return enumRawBodyLanguage.JSON;
            case "html":
                return enumRawBodyLanguage.HTML;
            case "xml":
                return enumRawBodyLanguage.XML;
            case "graphql":
                return enumRawBodyLanguage.GRAPHQL;
        }
        return null;
    }

    public void setLanguage(enumRawBodyLanguage newLanguage) {
        switch(newLanguage) {
            case JSON:
                this.language = "json";
                break;
            case JAVASCRIPT:
                this.language = "javascript";
                break;
            case HTML:
                this.language = "html";
                break;
            case XML:
                this.language = "xml";
                break;
            case GRAPHQL:
                this.language = "graphql";
                break;
            default:
                this.language = null;
        }
    }

    public PostmanBodyRaw(enumRawBodyLanguage language) {
        this.setLanguage(language);
    }
}
