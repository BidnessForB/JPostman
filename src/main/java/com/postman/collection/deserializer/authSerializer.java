package com.postman.collection.deserializer;
import com.postman.collection.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.util.Iterator;

public class authSerializer implements JsonSerializer<PostmanAuth> {

    @Override
    public JsonElement serialize(PostmanAuth src, Type typeOfSrc, JsonSerializationContext context)
        {
    JsonObject jsonAuth = new JsonObject();
    JsonArray vars = new JsonArray();
    
    jsonAuth.addProperty("type", src.getAuthTypeAsString());
    JsonElement typeProp = jsonAuth.get("type");
    JsonObject curJVar;
    Iterator keys = src.getAuthElements().keySet().iterator();
    String curKey;
    PostmanVariable curVar;
    while(keys.hasNext()) {
        curKey = (String)keys.next();
        curVar = src.getAuthElement(curKey);
        curJVar = new JsonObject();
        curJVar.addProperty("key",curVar.getKey());
        curJVar.addProperty("value",curVar.getValue());
        curJVar.addProperty("type", "string");
        vars.add(curJVar);
    }

    jsonAuth.add(src.getAuthTypeAsString(),vars);
    return jsonAuth;

        }       
};