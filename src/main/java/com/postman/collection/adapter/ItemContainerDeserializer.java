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

public class ItemContainerDeserializer implements JsonDeserializer<ItemGroup> {
    @Override
    public ItemGroup deserialize(JsonElement jElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = jElement.getAsJsonObject();
        Type typeItem = new TypeToken<ArrayList<ItemElement>>(){}.getType();
        Folder newFolder = null;
            newFolder = new Folder(jObject.getAsJsonPrimitive("name").getAsString());
            if(jObject.get("description") != null) {
                newFolder.setDescription(jObject.getAsJsonPrimitive("description").getAsString());
            }
            if(jObject.get("item") == null) {
                return newFolder;
            }
            ArrayList<ItemElement> items = context.deserialize(jObject.getAsJsonArray("item"), typeItem);
            newFolder.setItemElements(items);
            
            return newFolder;
        
    }
}
             