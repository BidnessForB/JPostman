package com.postman.collection;

public class PostmanGraphQL {
    private String query;
    private String variables;

    

    public PostmanGraphQL(String query) {
        this(query,null);
    }

    public PostmanGraphQL(String query, String variables) {
        this.query = query;
        this.variables = variables;
    }



    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public String getVariables() {
        return variables;
    }
    public void setVariables(String variables) {
        this.variables = variables;
    }
}
