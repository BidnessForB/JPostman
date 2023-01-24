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
import java.net.URISyntaxException;

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



/**
 * Encapsulates a Postman collection
 * 
 * 
 */
public class PostmanCollection extends PostmanItem {

    
    private ArrayList<PostmanVariable> variable = null;
    private PostmanAuth auth = null;
    private HashMap<String, String> info;

    
    /*
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
} */

    
    
    
    /** 
     * Moves an item in the array of items contained by this collection from one parent to another.  
     * 
     * 
     * @param itemToMoveKey
     * @param parentKey
     * @throws InvalidCollectionAction If either the parent or item to be moved aren't present in the <code>item</code> array
     */
    public void moveItem(String itemToMoveKey, String parentKey) throws Exception {
        PostmanItem itemToMove = this.getItem(itemToMoveKey);
        PostmanItem parent = this.getItem(parentKey);

        if (itemToMove == null || parent == null) {
            throw new Exception("Couldn't find item to move and/or parent");
        }

    }

    
    /** 
     * 
     * Add a response object to the request contained by this collection specified by <code>requestKey</code>
     * 
     * 
     * @param requestKey Key identifying the request to add the response to
     * @param response New response to add to the request
     * @throws InvalidCollectionAction If the specifyed request is not contained by this collection
     */
    public void addResponse(String requestKey, PostmanResponse response) throws InvalidCollectionAction {
        PostmanItem req = this.getItem(requestKey);
        if(req == null) {
            throw new InvalidCollectionAction("Request with key [" + requestKey + "] not found");
        }
        req.addResponse(response);

    }

    
    /** 
     * 
     * Convenience method to add an item with no child items to this collection.  
     * 
     * @param newFolder The new item to add
     * @throws Exception
     */
    public PostmanItem addFolder(String name) throws RecursiveItemAddException, IllegalPropertyAccessException {
        PostmanItem newItem = new PostmanItem(name);
        this.addItem(newItem);
        return newItem;

    }

    
    /** 
     * 
     * Create and add a new <code>request</code> as a top level child item of this collection.
     * 
     * 
     * @param newRequest The new request to add
     * @param name The name for the new item
     * @param response A response to include in the request item, or null to ignore
     * @return PostmanItem The new Request item
     * @throws RecursiveItemException If this collection already include this instance in it's array of items.
     * @throws IllegalPropertyException 
     *  
     */
    public PostmanItem addRequest(PostmanRequest newRequest, String name, PostmanResponse response) throws RecursiveItemAddException, IllegalPropertyAccessException {
        PostmanItem newItem = this.addRequest(newRequest, name);
        if(response != null) {
            newItem.addResponse(response);
        }
        return newItem;
    }

    
    /** 
     * 
     * Convenience method to add a new <code>request</code> item as a top level child item of this collection
     * 
     * @param newRequest
     * @param name
     * @return PostmanItem
     * @throws RecursiveItemException If this collection already include this instance in it's array of items.
     * @throws IllegalPropertyException 
     */
    public PostmanItem addRequest(PostmanRequest newRequest, String name) throws RecursiveItemAddException, IllegalPropertyAccessException  {
        PostmanItem newItem = new PostmanItem(name);
        newItem.setRequest(newRequest);
        super.addItem(newItem);
        return newItem;
        

    }

    
    /** 
     * 
     * Convenience method to add a request with a pre-constructed ArrayList&#60;{@link com.postman.collection.PostmanResponse}&#62; of response items.
     * 
     * @param newRequest The new Request to add
     * @param name The name of the request
     * @param responses Pre-constructed ArrayList of response objects.
     * @return PostmanItem The new request item.
     * @throws RecursiveItemException If this collection already include this instance in it's array of items.
     * @throws IllegalPropertyException 
     */
    public PostmanItem addRequest(PostmanRequest newRequest, String name, ArrayList<PostmanResponse> responses) throws RecursiveItemAddException, IllegalPropertyAccessException 
             {
        PostmanItem newItem = addRequest(newRequest, name);
        newItem.setResponses(responses);
        return newItem;
    }

    
    /** 
     * 
     * Add a new request to this collection at the specified position in the array of <code>request</code> elements
     * 
     * @param newRequest
     * @param name
     * @param position
     * @throws Exception
     */
    public void addRequest(PostmanRequest newRequest, String name, int position) throws RecursiveItemAddException, IllegalPropertyAccessException {
        PostmanItem newItem = new PostmanItem(name);
        newItem.setRequest(newRequest);
        super.addItem(newItem, position);
        

    }

    
    /** 
     * 
     * Move an item to a different parent item.  
     * 
     * @param itemToMove The item to move
     * @param newParent The item's new parent
     * @throws RecursiveItemAddException If the parent item is the same as the new child item, or if the parent item already contains this item.
     */
    public void moveItem(PostmanItem itemToMove, PostmanItem newParent) throws RecursiveItemAddException, InvalidCollectionAction {
        PostmanItem curParent = this.getItem(itemToMove.getKey(), true);
        if (itemToMove.equals(newParent)) {
            throw new RecursiveItemAddException("Can't move item to itself, yo");
        }

        if (curParent == null) {
            throw new InvalidCollectionAction("Item parent not found");
        }
        curParent.removeItem(itemToMove);
        try {
            newParent.addItem(itemToMove);
        }
        catch(IllegalPropertyAccessException e)
        {
            throw new InvalidCollectionAction(e);
        }
        
    }

    
    /** 
     * 
     * Set the array of key-value pairs in this collections <code>variable</code> array element
     * 
     * @param vars The ArrayList&#60;{@link com.postman.collection.PostmanVariable}&#62; containing the variables
     */
    public void setVariables(ArrayList<PostmanVariable> vars) {
        this.variable = vars;
    }

    
    /** 
     * 
     * Add or replace variable to the collection of variables comprising this collections <code>variable</code> array property.  If a variable with the same <code>key</code> already exists
     * in the collection it is replaced.
     * 
     * @param var
     */
    public void addVariable(PostmanVariable var) {
        if (this.variable == null) {
            this.variable = new ArrayList<PostmanVariable>();
        }
        if (this.getVariable(var.getKey()) != null) {
            //easier than getting the variable lol
            this.removeVariable(var.getKey());
        }
        this.variable.add(var);
    }

    
    /** 
     * 
     * Remove variable with the specified key from the array of key-value pairs comprising this collections <code>variable</code> array element.  
     * 
     * @param key Key of the variable to remove
     */
    public void removeVariable(String key) {
        if (this.variable == null) {
            return;
        }
        for(int i = 0; i < this.variable.size(); i++) {
            if (this.variable.get(i).getKey().equals(key)) {
                this.variable.remove(i);
                break;
            }
        }
    }

      /** 
     * 
     * Remove variable from the array of key-value pairs comprising this collections <code>variable</code> array element.  
     * 
     * @param var The variable to remove.  Matching is by the value of <code>key</code>
     */
    public void removeVariable(PostmanVariable var) {
        this.removeVariable(var.getKey());
    }

    
    /** 
     * 
     * Return the PostmanVariable key-value pair from this collection's <code>variable</code> array element, or null if it is not present.
     * 
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
     * 
     * <p>Add another collection and it's array of <code>item</code>,  <code>variable</code>, and  <code>event</code> arrays to this collection as children of <code>parent</code>
     * 
     * 
     * @param newColl The collection to combine with this one
     * @param parent
     @throws RecursiveItemAddException If the new collection is the same as this collection
     * @throws InvalidCollectionAction If the specified parent is not a folder (e.g., contains a request element)
     */
    public void addCollection(PostmanCollection newColl, PostmanItem parent) throws RecursiveItemAddException, InvalidCollectionAction {
        this.addCollection(newColl, parent, true, true);
    }

    
    /** 
     * 
     * <p>Add another collection and it's array of <code>item</code>, optionally <code>variable</code>, and optionally <code>event</code> arrays to this collection as children of a new Folder child item of this collection.
     * 
     * @param newColl The collection to combine with this one
     * @param copyScripts Whether to copy the source collections events to the new parent folder
     * @param copyVariables Whether to copy the source collections variables to the target collections array of <code>variable</code> elements.  Note there is no checking for namespace collisions.  
     * @throws RecursiveItemAddException If the new collection is the same as this collection
     * @throws InvalidCollectionAction If the specified parent is not a folder (e.g., contains a request element)
     */
    public void addCollection(PostmanCollection newColl, boolean copyScripts, boolean copyVariables) throws RecursiveItemAddException, InvalidCollectionAction  {
        this.addCollection(newColl, this, copyScripts, copyVariables);
    }

    
    /** 
     * <p>Add another collection and it's array of <code>item</code>, optionally <code>variable</code>, and optionally <code>event</code> arrays to this collection as children of the specified parent.
     * 
     * @param newColl The collection to combine with this one
     * @param parent The new parent object
     * @param copyScripts Whether to copy the source collections events to the new parent folder
     * @param copyVariables Whether to copy the source collections variables to the target collections array of <code>variable</code> elements.  Note there is no checking for namespace collisions.  
     * @throws RecursiveItemAddException If the new collection is the same as this collection
     * @throws InvalidCollectionAction If the specified parent is not a folder (e.g., contains a request element)
     */
    public void addCollection(PostmanCollection newColl, PostmanItem parent, boolean copyScripts, boolean copyVariables) throws RecursiveItemAddException, InvalidCollectionAction
             {
        if(parent == null || (!this.hasItem(parent))) {
            throw new InvalidCollectionAction("Parent is null or not an item in this collection");
        }
        PostmanItem newFolder = new PostmanItem(newColl.getName());
        try {
            parent.addItem(newFolder);
        }
        catch(IllegalPropertyAccessException e) {
            throw new InvalidCollectionAction(e);
        }
        
        
        newFolder.addItems(newColl.getItems());

        if (copyVariables) {

            
            if (newColl.getVariables() != null) {
                if (this.variable == null) {
                    this.variable = new ArrayList<PostmanVariable>();
                }
                this.variable.addAll(newColl.getVariables());
            }
        }

        if (copyScripts) {
            newFolder.setEvents(newColl.getEvents());
        }
    }

    
    /** 
     * 
     * <p>Add another collection and it's array of <code>item</code>, <code>variable</code>, and <code>event</code> arrays to this collection in a new Folder. The new folder will have the same name as the collection added</p>
     * 
     * 
     * 
     * @param newColl The collection to add
     * @throws RecursiveItemAddException If the new collection is the same as this collection
     * @throws InvalidCollectionAction
     */
    public void addCollection(PostmanCollection newColl) throws RecursiveItemAddException, InvalidCollectionAction  {

        this.addCollection(newColl, this, true, true);
    }

    
    /** 
     * Get the ArrayList&#60;{@link com.postman.collection.PostmanVariable PostmanVariable}&#62; containing the key-value pairs comprising the <code>variable</code> array element of this collection
     * 
     * @return ArrayList<PostmanVariable>
     */
    public ArrayList<PostmanVariable> getVariables() {
        return variable;
    }


    /** 
     * Construct a new, empty collection with the specified name
     */
    public PostmanCollection(String name) {
        super(name);

    }
    /**
     * 
     * Recursivel traverse the tree of <code>item</code> elements and link each item to it's parent explicitly
     * 
     */
    public void init() {
        
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
     * 
     * Static factory method to create the new collection.  Default constructor is undefined to avoid conflict with Gson.
     * 
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
            pmcRetVal.setSchemaURI(new URI(PostmanCollectionElement.getDefaultCollectionSchema()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pmcRetVal;
    }

    
    /** 
     * 
     * Static factory method to create a new PostmanCollection from a Postman collection JSON file.  
     * 
     * @param jsonFile  The Postman Collection JSON file to ingest
     * @return PostmanCollection The new collection
     * @throws FileNotFoundException If the specified JSON file does not exist 
     * @throws IOException If an IO exception occurs attempting to read the file, eg., inadequate permissions, etc.  
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
     * 
     * 
     * Set the value of this collections <code>info</code> object property with a populated Hash&#60;String&#62; containing the key-value pairs.  Note that 
     * the keys are not validated.
     * 
     * @param newInfo
     */
    public void setInfo(HashMap<String, String> newInfo) {
        this.info = newInfo;
    }

    
    /** 
     * 
     * Return the <code>_postman_id</code> property of the <code>info</code> object property of this collection
     * 
     * @return String The Postman ID
     */
    public String getPostmanID() {
        return this.info.get("_postman_id");
    }

    
    /** 
     * 
     * Set the <code>_postman_id</code> property of the <code>info</code> object property of this collection
     * 
     * @param id The new ID
     */
    public void setPostmanID(String id) {
        this.info.put("_postman_id", id);
    }

    
    /** 
     * Set the <code>_exporter_id</code> property of the <code>info</code> object property of this collection
     * 
     * @param id
     */
    public void setExporterID(String id) {
        this.info.remove("_exporter_id");
        this.info.put("_exporter_id", id);
    }

    
    /** 
     * 
     *  Set the <code>schema</code> property of the <code>info</code> object property of this collection
     * 
     * @param schemaURI
     */
    public void setSchemaURI(URI schemaURI) {

        this.info.put("schema", schemaURI.toString());
    }

    
    /** 
     * 
     * set all elements of the <code>info</code> property object of this collection.  Values are not validated
     * 
     * @param name The <code>name</code> property of the collection
     * @param description The <code>description</code> property of the collection
     * @param postmanID The <code>_postman_id</code> property of this collection
     * @param exporterID The  <code>_exporter_id</code> property of this colleciton
     * @param schemaURI The URI for the <code>schema</code> of this collection
     * @throws URISyntaxException If the provided string for <code>schemaURI</code> is not a properly constructed URI
     */
    public void setInfo(String name, String description, String postmanID, String exporterID, String schemaURI) throws URISyntaxException
             {
        this.setName(name);
        this.setDescription(description);
        this.setPostmanID(postmanID);
        this.setExporterID(exporterID);
        this.setSchemaURI(new URI(schemaURI));
        

    }

    
    /** 
     * 
     * Write this collections generated JSON to a file at the specified path.  Note that the order of elements in the resulting file is not guaranteed and may not match 
     * a corresponding Postman generated file.  However, this does not affect the validity or functionality of the generated JSON.
     * 
     * @param path The path to export JSON to.
     * @throws IOException If there is an error attempting to create or write to the specified path
     */
    public void writeToFile(String path) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(this.toJson());
        writer.close();
    }

    
    /** 
     * 
     * Set the <code>auth</code> object property of this collection with a {@link com.postman.collection.PostmanAuth} object containing the values.  
     * 
     * @param auth The new auth values, or null to remove an existing auth.
     */
    public void setAuth(PostmanAuth auth) {
        this.auth = auth;
    }

    
    /** 
     * 
     * Return the values in the <code>auth</code> object property of this collection, or null if it has not been set.
     * 
     * @return PostmanAuth The auth values, or null.  
     */
    public PostmanAuth getAuth() {
        return this.auth;
    }

    
    /** 
     * 
     * Generate JSON as text from the values in this collection.
     * 
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
     * 
     * Return a Map&#60;String&#62; containing the key-value pairs comprising the <code>info</code> object property of this collection.
     * 
     * @return Map<String, String> The key-value pairs.
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