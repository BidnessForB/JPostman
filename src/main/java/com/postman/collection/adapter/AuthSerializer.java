package com.postman.collection.adapter;

import com.postman.collection.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;

/**
 * 
 * 
 * Custom serializer for the <code>auth</code> element.
 * 
 * 
 */
public class AuthSerializer implements JsonSerializer<AuthElement> {

    
    /** 
     * @param src The PostmanAuth object to be deserialized
     * @param typeOfSrc The type, PostmanAuth
     * @param context Serialization context 
     * @return JsonElement The JSON element returned by this serializer
     */
    @Override
    public JsonElement serialize(AuthElement src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonAuth = new JsonObject();
        JsonArray vars = new JsonArray();

        jsonAuth.addProperty("type", src.getAuthTypeAsString());

        JsonObject curJVar;
        
        
        for (PostmanVariable curVar : src.getProperties()) {
            
            curJVar = new JsonObject();
            curJVar.addProperty("key", curVar.getKey());
            curJVar.addProperty("value", curVar.getValue());
            curJVar.addProperty("type", "string");
            vars.add(curJVar);
        }
        

        jsonAuth.add(src.getAuthTypeAsString(), vars);
        return jsonAuth;

    }
};
