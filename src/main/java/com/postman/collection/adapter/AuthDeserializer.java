package com.postman.collection.adapter;

import com.postman.collection.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;
import java.lang.reflect.Type;
/**
 * 
 * 
 * 
 * 
 * Custom deserializer for the <code>auth</code> property object.  
 * 
 * 
 * 
 */
public class AuthDeserializer implements JsonDeserializer<AuthElement> {

    /**
     * 
     * Custom <a href=
     * "https://www.javadoc.io/doc/com.google.code.gson/gson/2.6.2/com/google/gson/JsonDeserializer.html">
     * GSON deserializer</a> for the PostmanAuth object.
     * 
     * 
     * @param jElement The JSON element passed in by Gson
     * @param typeOfT The type for the adapter, PostmanAuth
     * @param context Deserialization context
     * @return PostmanAuth The assembed PostmanAuth object 
     * @throws JsonParseException IF there are errors in the JSON element
     */
    @Override
    public AuthElement deserialize(JsonElement jElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = jElement.getAsJsonObject();
        JsonObject curVar;
        String type = jObject.get("type").getAsString();
        JsonArray vars = jObject.get(type).getAsJsonArray();
        PostmanVariable pvVar;
        AuthElement auth = new AuthElement(jObject.get("type").getAsString());
        for (int i = 0; i < vars.size(); i++) {
            curVar = vars.get(i).getAsJsonObject();
            curVar.get("key");
            pvVar = new PostmanVariable(curVar.get("key").getAsString(), curVar.get("value").getAsString(), null,
                    curVar.get("type").getAsString());
            try {
                auth.addProperty(pvVar);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        
        return auth;

    }
}
