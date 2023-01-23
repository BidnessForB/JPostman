package com.postman.collection.adapter;

import com.postman.collection.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.HashMap;

public class varMapSerializer implements JsonSerializer<HashMap<String, PostmanVariable>> {
    
    /** 
     * @param src
     * @param typeOfSrc
     * @param context
     * @return JsonElement
     */
    public JsonElement serialize(HashMap<String, PostmanVariable> src, Type typeOfSrc,
            JsonSerializationContext context) {
        JsonArray varArray = new JsonArray();
        JsonObject varElement;
        String curKey;
        PostmanVariable var = null;
        Iterator<String> keys = src.keySet().iterator();
        while (keys.hasNext()) {
            curKey = keys.next();
            varElement = new JsonObject();
            var = src.get(curKey);
            varElement.addProperty("key", var.getKey());
            varElement.addProperty("value", var.getValue());
            if (var.getDescription() != null) {
                varElement.addProperty("description", var.getDescription());
            }
            if (var.getType() != null) {
                varElement.addProperty("type", var.getType());
            }
            varArray.add(varElement);
        }

        return varArray;

    }

}
