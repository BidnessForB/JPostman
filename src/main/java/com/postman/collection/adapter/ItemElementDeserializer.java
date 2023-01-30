package com.postman.collection.adapter;

import com.postman.collection.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * 
 * Custom deserializer for writing out the ItemElement class, the <code>item</code> propertyList 
 * 
 * 
 */



public class ItemElementDeserializer implements JsonDeserializer<ItemElement> {
    
    /**
     * 
     * Deserialize a {@link com.postman.collection.ItemElement }
     * 
     * 
     * @param jElement The JSON element passed in by Gson
     * @param typeOfT The type for the adapter, {@link com.postman.collection.AuthElement}
     * @param context Deserialization context
     * @return ItemAuth The assembed {@link com.postman.collection.ItemElement} object 
     * @throws JsonParseException IF there are errors in the JSON element
     */
    @Override
    public ItemElement deserialize(JsonElement jElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = jElement.getAsJsonObject();
        Request newRequest = null;
        
        if(jObject.get("request") == null) {
            return context.deserialize(jElement.getAsJsonObject(), ItemGroup.class);
            
        }
        Type typeEvent = new TypeToken<ArrayList<EventElement>>(){}.getType();
        Type typeResponse = new TypeToken<ArrayList<ResponseElement>>(){}.getType();
            newRequest = new Request(jObject.get("name").getAsString());
            if(jObject.get("description") != null) {
                newRequest.setDescription(jObject.get("description").getAsString());
            }
            
            RequestElement req = context.deserialize(jObject.getAsJsonObject("request"),RequestElement.class);
            newRequest.setRequestElement(req);
            ArrayList<EventElement> events = context.deserialize(jObject.getAsJsonArray("event"), typeEvent);
            newRequest.setEvents(events);
            ArrayList<ResponseElement> responses = context.deserialize(jObject.getAsJsonArray("response"), typeResponse);
            newRequest.setResponseElements(responses);
            return newRequest;
        
    }
}
             