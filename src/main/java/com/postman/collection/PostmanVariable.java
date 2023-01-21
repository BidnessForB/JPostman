package com.postman.collection;

public class PostmanVariable extends PostmanCollectionElement {
    private String key = "";
    private String value = "";
    private String description;
    private String type = "string";

    
    /** 
     * @return String
     */
    public String getToken() {
        return "{{" + key + "}}";
    }

    public PostmanVariable(String key, String value, String description) {
        this(key, value, description, null);

    }

    public PostmanVariable(String key, String value, String description, String type) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.type = type;
    }

    public PostmanVariable(String key, String value) {
        this(key, value, null, null);
    }

    
    /** 
     * @return String
     */
    public String getKey() {
        return key;
    }

    
    /** 
     * @param desc
     */
    public void setDescription(String desc) {
        this.description = desc;
    }

    
    /** 
     * @return String
     */
    public String getDescription() {
        return this.description;
    }

    
    /** 
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    
    /** 
     * @return String
     */
    public String getValue() {
        return value;
    }

    
    /** 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    
    /** 
     * @return String
     */
    public String getType() {
        return this.type;
    }

    
    /** 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

}
