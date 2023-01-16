package com.postman.collection;
import com.google.gson.Gson;

public class PostmanInfo implements IPostmanCollectionElement {
    private String _postman_id = "";
    private String name = "";
    private String description = "";
    private String schema = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json";
    private String _exporter_id;

    public String get_postman_id() {
        return _postman_id;
    }
    public void setPostmanId(String postmanId) {
        this._postman_id = postmanId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        return true;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSchema() {
        return schema;
    }
    public void setSchema(String schema) {
        this.schema = schema;
    }
    public String getExporterId() {
        return _exporter_id;
    }
    public void setExporterId(String exporterId) {
        this._exporter_id = exporterId;
    }
    @Override
    public String getKey() {
        
        return name;
    }
    @Override
    public void setKey(String key) {
        
        name = key;
    }
    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        
        return new Gson().toJson(this);
    }

    
}
