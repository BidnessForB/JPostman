package com.postman.collection;
import com.google.gson.Gson;

public class PostmanInfo implements IPostmanCollectionElement {
    private String _postman_id = "";
    private String name = "";
    private String description = "";
    private String schema = "";
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
        // TODO Auto-generated method stub
        return name;
    }
    @Override
    public void setKey(String key) {
        // TODO Auto-generated method stub
        name = key;
    }
    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        // TODO Auto-generated method stub
        return new Gson().toJson(this);
    }

    
}
