package com.postman.collection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.postman.collection.adapter.*;

import java.util.Set;
import java.util.Iterator;
import java.net.URI;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("unused")
public class PostmanCollection extends PostmanItem {

    // private PostmanInfo info = null;
    private ArrayList<PostmanVariable> variable = null;
    private PostmanAuth auth = null;

    public transient String tempSchemaJson;
    public HashMap<String, String> info;

    
    /** 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String filePath = new java.io.File("").getAbsolutePath();
        //String resourcePath = new java.io.File(filePath + "/src/main/resources/com/postman/collection/");
        PostmanCollection pmcTest = PostmanCollection.PMCFactory(new java.io.File(filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
        PostmanItem fact = pmcTest.getItem("Get a list of facts");
        PostmanItem folder = pmcTest.getItem("get Breeds",true);

        ArrayList<PostmanItem> folders = pmcTest.getItems(enumPostmanItemType.FOLDER);
        ArrayList<PostmanItem> requests = pmcTest.getItems(enumPostmanItemType.REQUEST);
        ArrayList<PostmanItem> all = pmcTest.getItems(null);

        fact = pmcTest.getItem("Add Breed");

        

                
                

        



    }

    
    /** 
     * @return String
     * @throws Exception
     */
    public static String getPostmanCollectionSchema() throws Exception {
        BufferedReader brItem;
        String strChunk = null;
        String schemaJSON = null;
        String filePath = new java.io.File("").getAbsolutePath();

        brItem = new BufferedReader(new FileReader(
                new File(filePath + "/src/main/resources/com/postman/collection/postman-collection-2.1-schema.json")));
        while ((strChunk = brItem.readLine()) != null)
            schemaJSON = schemaJSON + strChunk;
        try {
            brItem.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        schemaJSON = schemaJSON.substring(4);
        return schemaJSON;
    }

    
    /** 
     * @param itemToMoveKey
     * @param parentKey
     * @throws Exception
     */
    public void moveItem(String itemToMoveKey, String parentKey) throws Exception {
        PostmanItem itemToMove = this.getItem(itemToMoveKey);
        PostmanItem parent = this.getItem(parentKey);

        if (itemToMove == null || parent == null) {
            throw new Exception("Couldn't find item to move and/or parent");
        }

    }

    
    /** 
     * @param schemaJSON
     */
    public void setTemporarySchema(String schemaJSON) {
        this.tempSchemaJson = schemaJSON;
    }

    
    /** 
     * @param requestKey
     * @param response
     * @throws Exception
     */
    public void addResponse(String requestKey, PostmanResponse response) throws Exception {
        PostmanItem req = this.getItem(requestKey);
        req.addResponse(response);

    }

    
    /** 
     * @param newFolder
     * @throws Exception
     */
    public void addFolder(PostmanItem newFolder) throws Exception {
        if (newFolder.getItemType() != enumPostmanItemType.FOLDER) {
            throw new Exception("Item is not a folder");
        }

        this.addItem(newFolder);

    }

    
    /** 
     * @param newRequest
     * @param name
     * @param response
     * @return PostmanItem
     * @throws Exception
     */
    public PostmanItem addRequest(PostmanRequest newRequest, String name, PostmanResponse response) throws Exception {
        PostmanItem newItem = this.addRequest(newRequest, name);
        newItem.addResponse(response);
        return newItem;
    }

    
    /** 
     * @param newRequest
     * @param name
     * @return PostmanItem
     * @throws Exception
     */
    public PostmanItem addRequest(PostmanRequest newRequest, String name) throws Exception {
        PostmanItem newItem = new PostmanItem(name);
        newItem.setRequest(newRequest);
        super.addItem(newItem);

        return newItem;

    }

    
    /** 
     * @param newRequest
     * @param name
     * @param responses
     * @return PostmanItem
     * @throws Exception
     */
    public PostmanItem addRequest(PostmanRequest newRequest, String name, ArrayList<PostmanResponse> responses)
            throws Exception {
        PostmanItem newItem = addRequest(newRequest, name);
        newItem.setResponses(responses);
        return newItem;
    }

    
    /** 
     * @param newRequest
     * @param name
     * @param position
     * @throws Exception
     */
    public void addRequest(PostmanRequest newRequest, String name, int position) throws Exception {
        PostmanItem newItem = new PostmanItem(name);
        newItem.setRequest(newRequest);
        super.addItem(newItem, position);
        // newItem.setResponses(new PostmanResponse[0]);

    }

    
    /** 
     * @param itemToMove
     * @param newParent
     * @throws Exception
     */
    public void moveItem(PostmanItem itemToMove, PostmanItem newParent) throws Exception {
        PostmanItem curParent = this.getItem(itemToMove.getKey(), true);
        if (itemToMove.equals(newParent)) {
            throw new Exception("Can't move item to itself, yo");
        }

        if (curParent == null) {
            throw new Exception("Item parent not found");
        }
        curParent.removeItem(itemToMove);
        newParent.addItem(itemToMove);
    }

    
    /** 
     * @param vars
     */
    public void setVariables(ArrayList<PostmanVariable> vars) {
        this.variable = vars;
    }

    
    /** 
     * @param var
     */
    public void addVariable(PostmanVariable var) {
        if (this.variable == null) {
            this.variable = new ArrayList<PostmanVariable>();
        }
        if (this.getVariable(var.getKey()) != null) {
            this.removeVariable(var.getKey());
        }
        this.variable.add(var);
    }

    
    /** 
     * @param key
     */
    public void removeVariable(String key) {
        if (this.variable == null) {
            return;
        }
        for (int i = 0; i < this.variable.size(); i++)
            if (this.variable.get(i).getKey().equals(key)) {
                this.variable.remove(i);
                return;
            }
    }

    
    /** 
     * @param key
     * @return PostmanVariable
     */
    public PostmanVariable getVariable(String key) {
        if (this.variable == null) {
            return null;
        }
        for (PostmanVariable var : this.variable) {
            if (var.getKey().equals(key)) {
                return var;
            }
        }
        return null;
    }

    
    /** 
     * @param newColl
     * @param parent
     * @throws Exception
     */
    public void addCollection(PostmanCollection newColl, PostmanItem parent) throws Exception {
        this.addCollection(newColl, parent, true, true);
    }

    
    /** 
     * @param newColl
     * @param copyScripts
     * @param copyVariables
     * @throws Exception
     */
    public void addCollection(PostmanCollection newColl, boolean copyScripts, boolean copyVariables) throws Exception {
        this.addCollection(newColl, this, copyScripts, copyVariables);
    }

    
    /** 
     * @param newColl
     * @param parent
     * @param copyScripts
     * @param copyVariables
     * @throws Exception
     */
    public void addCollection(PostmanCollection newColl, PostmanItem parent, boolean copyScripts, boolean copyVariables)
            throws Exception {

        PostmanItem newFolder = new PostmanItem(newColl.getName());
        parent.addItem(newFolder);
        newFolder.addItems(newColl.getItems());

        if (copyVariables) {

            if (this.variable == null) {
                this.variable = new ArrayList<PostmanVariable>();
            }
            if (newColl.getVariables() != null) {
                this.variable.addAll(newColl.getVariables());
            }
        }

        if (copyScripts) {
            newFolder.setEvents(newColl.getEvents());
        }
    }

    
    /** 
     * @param newColl
     * @throws Exception
     */
    public void addCollection(PostmanCollection newColl) throws Exception {

        this.addCollection(newColl, this, true, true);
    }

    
    /** 
     * @return ArrayList<PostmanVariable>
     */
    public ArrayList<PostmanVariable> getVariables() {
        return variable;
    }



    public PostmanCollection(String name) {
        // this.info = new PostmanInfo();
        // this.info.setName(name);
        super(name);

    }

    public void init() {
        // System.out.println("We're in init");
        /*
         * if(this.getInfo() == null)
         * {
         * this.info = new PostmanInfo();
         * }
         */
        // set parents for all items.
        this.setParents();
        this.info = new HashMap<String, String>();

    }

    
    /** 
     * @param newName
     */
    public void setName(String newName) {
        this.info.put("name", newName);
    }

    private void setParents() {
        ArrayList<PostmanItem> folders = this.getItems(enumPostmanItemType.FOLDER);
        ArrayList<PostmanItem> requests = this.getItems(enumPostmanItemType.REQUEST);
        folders = folders == null ? new ArrayList<PostmanItem>() : folders;
        requests = requests == null ? new ArrayList<PostmanItem>() : requests;
        PostmanItem curParent = null;
        folders.addAll(requests);

        for (PostmanItem curItem : folders) {
            curParent = getItem(curItem.getKey(), true);
            curItem.setParent(curParent);
        }
    }

    
    /** 
     * @param desc
     */
    public void setDescription(String desc) {
        this.info.put("description", desc);
    }

    
    /** 
     * @return String
     */
    public String getDescription() {
        return this.info.get("description");
    }

    
    /** 
     * @return PostmanCollection
     */
    public static PostmanCollection PMCFactory() {

        String json = "{}";
        Gson gson = new Gson();
        PostmanCollection pmcRetVal = gson.fromJson(json, PostmanCollection.class);
        pmcRetVal.init();
        pmcRetVal.setName("New Collection");
        pmcRetVal.setDescription("Created by JPostman");
        try {
            pmcRetVal.setSchemaURI(new URI(PostmanCollectionElement.defaultCollectionSchema));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pmcRetVal;
    }

    
    /** 
     * @param jsonFile
     * @return PostmanCollection
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static PostmanCollection PMCFactory(File jsonFile) throws FileNotFoundException, IOException {

        String strChunk = "";
        BufferedReader brItem = null;
        String strJson = "";
        com.google.gson.Gson gson = null;
        PostmanCollection pmcRetVal;
        brItem = new BufferedReader(new FileReader(jsonFile));
        while ((strChunk = brItem.readLine()) != null)
            strJson = strJson + strChunk;
        try {
            brItem.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PostmanAuth.class, new com.postman.collection.adapter.authDeserializer());
        pmcRetVal = gsonBuilder.create().fromJson(strJson, PostmanCollection.class);

        System.out.println(pmcRetVal.getName());

        return pmcRetVal;
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return this.info.get("name");
    }

    
    /** 
     * @param newInfo
     */
    public void setInfo(HashMap<String, String> newInfo) {
        this.info = newInfo;
    }

    
    /** 
     * @return String
     */
    public String getPostmanID() {
        return this.info.get("_postman_id");
    }

    
    /** 
     * @param id
     */
    public void setPostmanID(String id) {
        this.info.put("_postman_id", id);
    }

    
    /** 
     * @param id
     */
    public void setExporterID(String id) {
        this.info.remove("_exporter_id");
        this.info.put("_exporter_id", id);
    }

    
    /** 
     * @param schemaURI
     */
    public void setSchemaURI(URI schemaURI) {

        this.info.put("schema", schemaURI.toString());
    }

    
    /** 
     * @param name
     * @param description
     * @param postmanID
     * @param exporterID
     * @param schemaURI
     * @throws Exception
     */
    public void setInfo(String name, String description, String postmanID, String exporterID, String schemaURI)
            throws Exception {
        this.setName(name);
        this.setDescription(description);
        this.setPostmanID(postmanID);
        this.setExporterID(exporterID);
        this.setSchemaURI(new URI(schemaURI));

    }

    
    /** 
     * @param path
     * @throws IOException
     */
    public void writeToFile(String path) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(this.toJson());
        writer.close();
    }

    
    /** 
     * @param auth
     */
    public void setAuth(PostmanAuth auth) {
        this.auth = auth;
    }

    
    /** 
     * @return PostmanAuth
     */
    public PostmanAuth getAuth() {
        return this.auth;
    }

    
    /** 
     * @return String
     */
    public String toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        JsonSerializer<ArrayList<PostmanVariable>> varSerializer = new JsonSerializer<ArrayList<PostmanVariable>>() {
            public JsonElement serialize(ArrayList<PostmanVariable> src, Type typeOfSrc,
                    JsonSerializationContext context) {
                JsonArray varArray = new JsonArray();
                JsonObject varElement;

                for (PostmanVariable var : src) {
                    varElement = new JsonObject();
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
        };

        JsonSerializer<HashMap<String, String>> mapSerializer = new JsonSerializer<HashMap<String, String>>() {
            public JsonElement serialize(HashMap<String, String> src, Type typeOfSrc,
                    JsonSerializationContext context) {
                JsonObject jsonMap = new JsonObject();

                Iterator<String> keys = src.keySet().iterator();
                String curKey;
                while (keys.hasNext()) {
                    curKey = keys.next();
                    jsonMap.addProperty(curKey, src.get(curKey));
                }

                return jsonMap;
            }
        };

        /*
        JsonSerializer<HashMap<String, PostmanVariable>> varMapSerializer = new JsonSerializer<HashMap<String, PostmanVariable>>() {
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
        };
        */

        Type mapType = new TypeToken<HashMap<String, String>>() {
        }.getType();
        Type varMapType = new TypeToken<HashMap<String, PostmanVariable>>() {
        }.getType();

        Type varType = new TypeToken<ArrayList<PostmanVariable>>() {
        }.getType();

        // gsonBuilder.registerTypeAdapter(PostmanAuth.class, serializer);
        gsonBuilder.registerTypeAdapter(mapType, mapSerializer);
        // gsonBuilder.registerTypeAdapter(varType, varSerializer);
        gsonBuilder.registerTypeAdapter(varMapType, new com.postman.collection.adapter.varMapSerializer());
        gsonBuilder.registerTypeAdapter(PostmanAuth.class, new com.postman.collection.adapter.authSerializer());

        Gson customGson = gsonBuilder.create();
        String customJSON = customGson.toJson(this);
        return customJSON;
    }

    
    /** 
     * @param key
     * @return PostmanItem
     */
    public PostmanItem getItemParent(String key) {
        return this.getItem(key, true);
    }

    
    /** 
     * @return Map<String, String>
     */
    public Map<String, String> getInfo() {
        return this.info;
    }

    
    /** 
     * @return String
     */
    @Override
    public String getKey() {

        return null;
    }

}