package com.postman.collection.adapter;

import com.postman.collection.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;
import java.lang.reflect.Type;

public class AuthDeserializer implements JsonDeserializer<PostmanAuth> {

    /**
     * 
     * Custom <a href=
     * "https://www.javadoc.io/doc/com.google.code.gson/gson/2.6.2/com/google/gson/JsonDeserializer.html">
     * GSON deserializer</a> for the PostmanAuth object.
     * 
     * 
     * @param jElement
     * @param typeOfT
     * @param context
     * @return PostmanAuth
     * @throws JsonParseException
     */
    @Override
    public PostmanAuth deserialize(JsonElement jElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = jElement.getAsJsonObject();
        JsonObject curVar;
        String type = jObject.get("type").getAsString();
        JsonArray vars = jObject.get(type).getAsJsonArray();
        PostmanVariable pvVar;
        PostmanAuth auth = new PostmanAuth(jObject.get("type").getAsString());
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