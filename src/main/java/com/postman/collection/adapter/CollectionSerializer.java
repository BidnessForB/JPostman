package com.postman.collection.adapter;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;
import com.postman.collection.*;
import java.lang.reflect.Type;


public class CollectionSerializer implements JsonSerializer<PostmanCollection> {
    
    @Override
    public JsonElement serialize(PostmanCollection src, Type typeOfSrc, JsonSerializationContext context) {
        
        JsonObject collJsonMap = new JsonObject();
        
        collJsonMap.add("info", context.serialize(src.getInfo()));
        collJsonMap.add("item",context.serialize(src.getItems()));
        collJsonMap.add("event", context.serialize(src.getEvents()));
        collJsonMap.add("variable", context.serialize(src.getVariables()));
        collJsonMap.add("auth",context.serialize(src.getAuth()));

        return collJsonMap;
    }

}
