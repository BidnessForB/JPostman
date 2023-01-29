package com.postman.collection;

public class PostmanID {
    private String ID;
    public PostmanID(String ID) {
        this.ID = ID;
    }

    
    /** 
     * @return String
     */
    public String toString() {
        return this.ID;
    }

    
    /** 
     * @return String
     */
    public String getID() {
        return this.ID;
    }
}
