package com.postman.collection;

import com.google.gson.Gson;
public class PostmanScript implements IPostmanCollectionElement {
    private String type = "";
    private String[] exec;

    
    public PostmanScript(String scriptType, String[] Exec) {
            this.type = scriptType;
            exec = Exec;
    }

    public PostmanScript(String scriptType, String srcCode)  {
        this.exec = new String[1];
        this.exec[0] = srcCode;
        this.type = scriptType;

    }



    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String[] getExec() {
        return exec;
    }


    public void setExec(String[] exec) {
        this.exec = exec;
    }


    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void setKey(String key) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        // TODO Auto-generated method stub
        return new Gson().toJson(this);
    }

    

}
