package com.postman.collection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;
import java.util.Map;




import java.util.HashMap;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.net.http.HttpResponse.BodyHandlers;

import java.util.regex.Pattern;
import java.util.regex.Matcher;




/**
 * Encapsulates a Postman collection
 *<p>
 * <strong>Ingest a collection file</strong></p>
 * <p><code>Collection myCollection = PMCFactory(new File("example-cat-facts-with-tests.postman_collection.json");</code></p>
 * 
 * 
 * <p><strong>Get a request item</strong></p>
 * <p><code>ItemElement myReq = myCollection.getItem("Get Random Fact");</code></p>
 * 
 * <p><strong>Get pre-request script for the request</strong></p>
 * 
 * <p><code>EventElement preReq = myReq.getPreRequestScript();</code></p>
 
 * <p><strong> Get source code for pre-request script</strong></p>
 * <p><code>String myCode = preReq.getSourceCode();</code></p>
 * 
 * 
 *  
 * 
 * 
 * 
 */
public class Collection extends ItemGroup {

    
    
    private AuthElement auth = null;
    private HashMap<String, String> info;
    private VariableListMap<PostmanVariable> variable = null;
    
 
    /** 
     * Moves an item in the array of items contained by this collection from one parent to another.  
     * 
     * 
     * @param itemToMoveKey
     * @param parentKey
     * @throws InvalidCollectionActionException If either the parent or item to be moved aren't present in the <code>item</code> array
     */
    public void moveItem(String itemToMoveKey, String parentKey) throws RecursiveItemAddException, InvalidCollectionActionException {
        ItemElement itemToMove = this.getItemElement(itemToMoveKey);
        Folder parent = this.getFolder(parentKey);

        if (itemToMove == null || parent == null) {
            throw new InvalidCollectionActionException("Attempt to move a null item, or an item to a null parent");
        }

        
        this.moveItem(itemToMove, parent);

    }

    
    

    
    /** 
     * 
     * Convenience method to add an item with no child items to this collection.  
     * 
     * @param name The name for the new item
     * @throws RecursiveItemAddException
     * @throws IllegalPropertyAccessException
     */
    public Folder addFolder(String name) throws RecursiveItemAddException, IllegalPropertyAccessException {
        Folder newFolder = new Folder(name);
        this.addItemElement(newFolder);
        return newFolder;

    }

    
    /** 
     * 
     * Create and add a new <code>request</code> as a top level child item of this collection.
     * 
     * 
     * @param newRequest The new request to add
     * @param name The name for the new item
     * @param response A response to include in the request item, or null to ignore
     * @return ItemElement The new Request item
     * @throws RecursiveItemAddException If this collection already include this instance in it's array of items.
     * @throws IllegalPropertyAccessException 
     *  
     */
    public Request addRequest(RequestElement reqElement, String name, ResponseElement response) throws RecursiveItemAddException, IllegalPropertyAccessException {
        Request newReq = this.addRequest(reqElement,name);
        if(response != null) {
            newReq.addResponseElement(response);
        }
        return newReq;
    }

    
    /** 
     * 
     * Convenience method to add a new <code>request</code> item as a top level child item of this collection
     * 
     * @param reqElement
     * @param name
     * @return ItemElement
     * @throws RecursiveItemAddException If this collection already include this instance in it's array of items.
     * @throws IllegalPropertyAccessException 
     */
    public Request addRequest(RequestElement reqElement, String name) throws RecursiveItemAddException, IllegalPropertyAccessException  {
        Request newReq = new Request(reqElement, name);
        super.addItemElement(newReq);
        return newReq;
    }

    
    /** 
     * 
     * Convenience method to add a request with a pre-constructed ArrayList&#60;{@link com.postman.collection.ResponseElement}&#62; of response items.
     * 
     * @param newRequest The new Request to add
     * @param name The name of the request
     * @param responses Pre-constructed ArrayList of response objects.
     * @return ItemElement The new request item.
     * @throws RecursiveItemAddException If this collection already include this instance in it's array of items.
     * @throws IllegalPropertyAccessException 
     */
    public Request addRequest(RequestElement newRequest, String name, ArrayList<ResponseElement> responses) throws RecursiveItemAddException, IllegalPropertyAccessException 
             {
        Request newReq = addRequest(newRequest, name);
        newReq.setResponseElements(responses);
        return newReq;
    }

    
    /** 
     * 
     * Add a new request to this collection at the specified position in the array of <code>request</code> elements
     * 
     * @param reqElement
     * @param name
     * @param position
     * @throws RecursiveItemAddException If this collection already include this instance in it's array of items.
     * @throws IllegalPropertyAccessException 
     */
    public Request addRequest(RequestElement reqElement, String name, int position) throws RecursiveItemAddException, IllegalPropertyAccessException {
        Request newReq = new Request(reqElement, name);
        newReq.setRequestElement(reqElement);
        reqElement.setParent(newReq);
        super.addItemElement(newReq, position);
        return newReq;
        

    }

    
    /** 
     * 
     * Move an item to a different parent item.  
     * 
     * @param itemToMove The item to move
     * @param newParent The item's new parent
     * @throws RecursiveItemAddException If the parent item is the same as the new child item, or if the parent item already contains this item.
     */
    public void moveItem(ItemElement itemToMove, ItemGroup newParent) throws RecursiveItemAddException, InvalidCollectionActionException {
        ItemGroup curParent = itemToMove.getParent();
        if (itemToMove.equals(newParent)) {
            throw new RecursiveItemAddException("Can't move item to itself, yo");
        }

        if (curParent == null) {
            throw new InvalidCollectionActionException("Item parent not found");
        }
        curParent.removeItemElement(itemToMove);
        try {
            newParent.addItemElement(itemToMove);
        }
        catch(IllegalPropertyAccessException e)
        {
            throw new InvalidCollectionActionException(e);
        }
        
    }

    
    

    
    /** 
     * 
     * <p>Add another collection and it's array of <code>item</code>,  <code>variable</code>, and  <code>event</code> arrays to this collection as children of <code>parent</code>
     * 
     * 
     * @param newColl The collection to combine with this one
     * @param parent
     @throws RecursiveItemAddException If the new collection is the same as this collection
     * @throws InvalidCollectionActionException If the specified parent is not a folder (e.g., contains a request element)
     */
    public void addCollection(Collection newColl, ItemGroup parent) throws RecursiveItemAddException, InvalidCollectionActionException, IllegalPropertyAccessException{
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
     * @throws InvalidCollectionActionException If the specified parent is not a folder (e.g., contains a request element)
     */
    public void addCollection(Collection newColl, boolean copyScripts, boolean copyVariables) throws RecursiveItemAddException, InvalidCollectionActionException, IllegalPropertyAccessException  {
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
     * @throws InvalidCollectionActionException If the specified parent is not a folder (e.g., contains a request element)
     */
    public void addCollection(Collection newColl, ItemGroup parent, boolean copyScripts, boolean copyVariables) throws RecursiveItemAddException, InvalidCollectionActionException, IllegalPropertyAccessException
             {
        if(parent == null || (!this.hasItemElement(parent))) {
            throw new InvalidCollectionActionException("Parent is null or not an item in this collection");
        }
        Folder newFolder = new Folder(newColl.getName());
        try {
            parent.addItemElement(newFolder);
        }
        catch(IllegalPropertyAccessException e) {
            throw new InvalidCollectionActionException(e);
        }
        
        
        newFolder.addItemElements(newColl.getItemElements());

        if (copyVariables) {
            this.addVariables(newColl.getVariables());
        }
        

        if (copyScripts && newColl.getEvents() != null) {
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
     * @throws InvalidCollectionActionException
     */
    public void addCollection(Collection newColl) throws RecursiveItemAddException, InvalidCollectionActionException, IllegalPropertyAccessException  {

        this.addCollection(newColl, this, true, true);
    }

    
    

    /** 
     * Construct a new, empty collection with the specified name
     */
    public Collection(String name) {
        super(name);

    }
    /**
     * 
     * Recursivel traverse the tree of <code>item</code> elements and link each item to it's parent explicitly
     * 
     */
    private void init() {
        
        this.setParents();
        if(this.info == null) {
            this.info = new HashMap<String, String>();
        }
        if(this.getItemElements() == null) {
            //the item element is required by the Collection schema, even if it is empty
            this.setItemElements(new ArrayList<ItemElement>());
        }
        
    }

    
    /** 
     * @param newName
     */
    @Override
    public void setName(String newName) {
        this.info.put("name", newName);
    }

    private void setParents() {
        ArrayList<ItemElement> folders = this.getItemElements(enumItemElementType.FOLDER);
        ArrayList<ItemElement> requests = this.getItemElements(enumItemElementType.REQUEST);
        folders = folders == null ? new ArrayList<ItemElement>() : folders;
        requests = requests == null ? new ArrayList<ItemElement>() : requests;
        ItemElement curParent = null;
        folders.addAll(requests);

        for (ItemElement curItem : folders) {
            curParent = curItem.getParent();
            curItem.setParent(curParent);
        }
    }



    
    /** 
     * @param desc
     */
    @Override
    public void setDescription(String desc) {
        this.info.put("description", desc);
    }

    
    /** 
     * @return String
     */
    @Override
    public String getDescription() {
        return this.info.get("description");
    }

    
    /** 
     * 
     * Static factory method to create the new collection.  Default constructor is undefined to avoid conflict with Gson.
     * 
     * @return Collection
     */
    public static Collection pmcFactory() {

        String json = "{}";
        Gson gson = new Gson();
        Collection pmcRetVal = gson.fromJson(json, Collection.class);
        pmcRetVal.init();
        pmcRetVal.setName("New Collection");
        pmcRetVal.setDescription("Created by JPostman");
        try {
            pmcRetVal.setSchemaURI(new URI(CollectionElement.getDefaultCollectionSchema()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pmcRetVal;
    }

    
    /** 
     * 
     * Static factory method to create a new Collection from a Postman collection JSON file.  
     * 
     * @param jsonFile  The Postman Collection JSON file to ingest
     * @return Collection The new collection
     * @throws FileNotFoundException If the specified JSON file does not exist 
     * @throws IOException If an IO exception occurs attempting to read the file, eg., inadequate permissions, etc.  
     */
    public static Collection pmcFactory(File jsonFile) throws IOException {

        String strChunk = "";
        StringBuilder sbJson = new StringBuilder();

        try(FileReader fr = new FileReader(jsonFile);
            BufferedReader brItem  =new BufferedReader(fr)) {
            
            while ((strChunk = brItem.readLine()) != null)
                sbJson.append(strChunk);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collection.pmcFactory(sbJson.toString());

        
    }

    
    /** 
     * @param json
     * @return Collection
     */
    public static Collection pmcFactory(String json) {
        Collection pmcRetVal;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AuthElement.class, new com.postman.collection.adapter.AuthDeserializer());
        gsonBuilder.registerTypeAdapter(PostmanVariable.class, new com.postman.collection.adapter.PostmanVariableDeserializer());
        gsonBuilder.registerTypeAdapter(ItemElement.class, new com.postman.collection.adapter.ItemElementDeserializer());
        gsonBuilder.registerTypeAdapter(ItemGroup.class, new com.postman.collection.adapter.ItemGroupDeserializer());
        pmcRetVal = gsonBuilder.create().fromJson(json, Collection.class);
        pmcRetVal.init();

        return pmcRetVal;
    }
    /**
     * 
     * Create a collection via the Postman API
     * 
     * @param collectionURL  URL for the collection to ingest
     * @return Collection The new collection
     * @throws IOException  
     * @throws InterruptedException 
     * @throws IllegalArgumentException 
     * @throws CollectionNotFoundException If there is no collection at the specified URL
     * @throws ValidationException If the JSON returned by the specified URL does not conform to the Postman schema
     * @throws InvalidCollectionActionException If any other error occurs during the generation of the collection
     */
    public static Collection pmcFactory(URL collectionURL) throws IOException, InterruptedException, IllegalArgumentException, CollectionNotFoundException, ValidationException, InvalidCollectionActionException {
        // create a client
            var client = HttpClient.newHttpClient();
            String strColJson;
            Collection pmcRetVal;
            String apiToken = System.getenv("POSTMAN_API_KEY");
            if(apiToken == null) {
                throw new IllegalArgumentException("No Postman API Key configured");
            }

            // create a request
            var request = HttpRequest.newBuilder(
                URI.create(collectionURL.toString()))
            .header("accept", "application/json")
            .header("x-api-key",apiToken)
            .build();

            // use the client to send the request
            var response = client.send(request, BodyHandlers.ofString());
            
            
            if(response.statusCode() == 404) {
                throw new CollectionNotFoundException("Collection not found or invalid endopint");
            }
            else if(response.statusCode() != 200)
            {
                throw new InvalidCollectionActionException("An error occurred retrieving the collection" + (response.body() == null ? "[no response info]" : response.body()));
            }
            
            
            // the response:
            
            //Strip out the top-level "collection" key and the trailing brace
            strColJson = response.body();
            strColJson = strColJson.substring(14);
            strColJson = strColJson.substring(0,strColJson.length() -1);
            
               pmcRetVal = Collection.pmcFactory(strColJson);

            if(pmcRetVal.validate()) {
                
                return pmcRetVal;
            }
            else {
                throw new ValidationException("Invalid JSON returned from server");
            }
            
    }

    
    /** 
     * @param id
     * @return Collection
     * @throws IOException
     * @throws InterruptedException
     * @throws CollectionNotFoundException
     * @throws ValidationException
     * @throws InvalidCollectionActionException
     */
    public static Collection pmcFactory(PostmanID id) throws IOException, InterruptedException, CollectionNotFoundException, ValidationException, InvalidCollectionActionException {
        return Collection.pmcFactory(new URL("https://api.getpostman.com/collections/" + id));
    }

    /** 
     * @return String
     */
    @Override
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
     * @param outputFile The file into which to write the JSON
     * @throws IOException If there is an error attempting to create or write to the specified path
     */
    public void writeToFile(File outputFile) throws IOException {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
      
        writer.write(this.toJson());
        
        }
        catch(IOException e)
        {
            throw(e);
        }
    }

    
    /** 
     * 
     * Set the <code>auth</code> object property of this collection with a {@link com.postman.collection.AuthElement} object containing the values.  
     * 
     * @param auth The new auth values, or null to remove an existing auth.
     */
    public void setAuth(AuthElement auth) {
        this.auth = auth;
    }

    
    /** 
     * 
     * Return the values in the <code>auth</code> object property of this collection, or null if it has not been set.
     * 
     * @return AuthElement The auth values, or null.  
     */
    public AuthElement getAuth() {
        return this.auth;
    }

    
    
    /** 
     * @param src
     * @return String
     * @throws VariableResolutionException
     */
    public  String resolveVariables(String src) throws VariableResolutionException {

        Pattern pnVar = Pattern.compile("(?<!\\{)\\{\\{(?:([^{}]+)|\\{([^{}]+)})}}(?!})");
        Matcher maVar = pnVar.matcher(src);
        String curVarName;
        String curVarValue;
        String strResolved = src;
        
        PostmanVariable curVar;
        while(maVar.find()) {
            
            for(int i = 1; i < maVar.groupCount(); i = i + 2 ) {
                curVarName = maVar.group(i);
                curVar = this.getVariable(curVarName);
                {
                    if(curVar == null) {
                        throw new VariableResolutionException("No entry found for variable: " + curVarName);
                    }
                }
                curVarValue = this.getVariable(curVarName).getValue();
                strResolved = strResolved.replace("{{" + curVarName + "}}",curVarValue);
            }
        }
    
        return strResolved;
        


}

    
    /** 
     * 
     * Generate JSON as text from the values in this collection.
     * 
     * @return String
     */
    @Override
    public String toJson() {
        
        GsonBuilder gsonBuilder = new GsonBuilder();

        Type mapType = new TypeToken<HashMap<String, String>>() {
        }.getType();
        Type varMapType = new TypeToken<HashMap<String, PostmanVariable>>() {
        }.getType();


        gsonBuilder.registerTypeAdapter(mapType, new com.postman.collection.adapter.StringMapSerializer());
        gsonBuilder.registerTypeAdapter(varMapType, new com.postman.collection.adapter.VarMapSerializer());
        gsonBuilder.registerTypeAdapter(AuthElement.class, new com.postman.collection.adapter.AuthSerializer());
        gsonBuilder.registerTypeAdapter(com.postman.collection.Collection.class, new com.postman.collection.adapter.CollectionSerializer());
        gsonBuilder.registerTypeAdapter(com.postman.collection.PostmanVariable.class, new com.postman.collection.adapter.PostmanVariableSerializer());

        Gson customGson = gsonBuilder.create();
        return customGson.toJson(this);
        
    }

    
    
    /** 
     * 
     * Return a Map&#60;String&#62; containing the key-value pairs comprising the <code>info</code> object property of this collection.
     * 
     * @return Map&#60;String&#62; The key-value pairs.
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

 /** 
     * 
     * Set the array of key-value pairs in this collections <code>variable</code> array element
     * 
     * @param vars The ArrayList&#60;{@link com.postman.collection.PostmanVariable}&#62; containing the variables
     */
    public void setVariables(VariableListMap<PostmanVariable> vars) {
        this.variable = vars;
    }

    
    /** 
     * 
     * Add or replace variable to the collection of variables comprising this collections <code>variable</code> array property.  If a variable with the same <code>key</code> already exists
     * in the collection it is replaced.
     * 
     * @param varNew
     */
    public void addVariable(PostmanVariable varNew) {
        if(this.variable == null) {
            this.variable = new VariableListMap<PostmanVariable>();
        }
        this.variable.add(varNew);
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
        this.variable.remove(key);
        

    }

      /** 
     * 
     * Remove variable from the array of key-value pairs comprising this collections <code>variable</code> array element.  
     * 
     * @param varNew The variable to remove.  Matching is by the value of <code>key</code>
     */
    public void removeVariable(PostmanVariable varNew) {
        this.removeVariable(varNew.getKey());
    }

    
    /** 
     * 
     * Return the PostmanVariable key-value pair from this collection's <code>variable</code> array element, or null if it is not present.
     * 
     * @param key
     * @return PostmanVariable
     */
    public PostmanVariable getVariable(String key) {
            return this.variable.get(key);
        
    }

    /** 
     * Get the ArrayList&#60;{@link com.postman.collection.PostmanVariable PostmanVariable}&#62; containing the key-value pairs comprising the <code>variable</code> array element of this collection
     * 
     * @return ArrayList&#60;{@link com.postman.collection.PostmanVariable PostmanVariable}&#62;
     */
    public VariableListMap<PostmanVariable> getVariables() {
        return this.variable;
    }

    
    /** 
     * @param newVars
     */
    public void addVariables(VariableListMap<PostmanVariable> newVars) {
        if(newVars == null) {
            return;
        }
        if(this.variable == null) {
            this.variable = new VariableListMap<PostmanVariable>();
        }
        this.variable.addAll(newVars);
    }   

    
}