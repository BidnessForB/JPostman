package com.postman.collection;

import com.google.gson.*;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class PostmanAuth implements IPostmanCollectionElement {
    private transient String key = UUID.randomUUID().toString();    
    private String type = "";
    private PostmanVariable[] apikey = null;

    
    
    public String getType() {
        return type;
    }

    
    @Override
    public String getKey() {
        
        return type;
    }


    public boolean isValid() {
        return true;
    }

    


    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        
        return new Gson().toJson(this);
    }


    public void setType(String type) {
        this.type = type;
    }

    public PostmanVariable[] getApikey() {
        return apikey;
    }

    public void setApikey(PostmanVariable[] apikey) {
        this.apikey = apikey;
    }

    public void setAPIKey(String value, String type) {

    }

    public PostmanVariable getAPIKey() {
        return null;
    }

    public void setAPIValue(String value, String type) {
        
    }

    public PostmanVariable getAPIValue() {
        return this.getAPIElement("value");
    }

    public PostmanVariable getAPIElement(String key) {
        List<PostmanVariable> liElems;
        if(this.apikey == null)
        {
            liElems = new ArrayList<PostmanVariable>(Arrays.asList(new PostmanVariable[0]));
        }
        else
          {
            liElems = new ArrayList<PostmanVariable>(Arrays.asList(this.apikey));
          }

          for(int i = 0; i < liElems.size(); i++)
          {
            if(liElems.get(i).getKey().equals(key)) {
                return liElems.get(i);
            }
          }
          return null;
    }

    private void setAPIElement(String key, PostmanVariable newElement) {
        PostmanVariable apiElem = this.getAPIElement(key);
        List<PostmanVariable> liElems;
        if(apiElem != null)
        {
            apiElem = newElement;
            return;
        }
        
        if(this.apikey == null)
        {
            liElems = new ArrayList<PostmanVariable>(Arrays.asList(new PostmanVariable[0]));
        }
        else
          {
            liElems = new ArrayList<PostmanVariable>(Arrays.asList(this.apikey));
          }
        liElems.add(newElement);
        this.apikey = liElems.toArray(new PostmanVariable[0]);
    }

    

    public void addApiKey(String value, String type) {
        PostmanVariable newKey = new PostmanVariable("key",value);
        newKey.setType(type);
        this.setAPIElement("key", newKey);
        
    }

    

    public void addApiValue(String value, String type ) {
        PostmanVariable newValue = new PostmanVariable(value, type);
        newValue.setKey("value");
        this.setAPIElement("value",newValue);
    }

    public PostmanAuth(String type, PostmanVariable[] apikey) {
        this.type = type;
        this.apikey = apikey;
    }

}