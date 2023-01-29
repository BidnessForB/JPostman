package com.postman.collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
    /**
     * 
     * Encapsulates the <code>item</code> object in the postman schema

     * <pre>
     * {
    "name": "Get a list of facts",

    "event": [
        {
            "listen": "test",
            "script": {
                "exec": [
                    "pm.test(\"Status code is 200\", function () {",
                    "    pm.response.to.have.status(200);",
                    "});",
                    "",
                    "var latencyTestName = \"Response time is less than \" + pm.collectionVariables.get(\"latencyLimit\") + \" ms\";",
                    "",
                    "pm.test(latencyTestName, function () {",
                    "    pm.expect(pm.response.responseTime).to.be.below(parseInt(pm.collectionVariables.get(\"latencyLimit\")));",
                    "});",
                    "",
                    "pm.test(\"Response contains fact\", function () {",
                    "    var jsonData = pm.response.json();",
                    "    pm.expect(pm.response.json().length).to.be.greaterThan(1);",
                    "});"
                ],
                "type": "text/javascript"
            }
        },
        {
            "listen": "prerequest",
            "script": {
                "exec": [
                    "console.log(\"last fact: \" + pm.collectionVariables.get(\"curFact\"));"
                ],
                "type": "text/javascript"
            }
        }
    ],
    
    "request": {
        "method": "GET",
        "header": [
            {
                "key": "Accept",
                "value": "application/json"
            }
        ],
        "url": {
            "raw": "{{baseUrl}}/facts?max_length=200&amp;limit=2",
            "host": [
                "{{baseUrl}}"
            ],
            "path": [
                "facts"
            ],
            "query": [
                {
                    "key": "max_length",
                    "value": "200"
                },
                {
                    "key": "limit",
                    "value": "2",
                    "description": "limit the amount of results returned"
                }
            ]
        },
        "description": "Returns a a list of facts"
    },
    "response": [
        {
            "name": "successful operation",
            "originalRequest": {
                "method": "GET",
                "header": [],
                "url": {
                    "raw": "{{baseUrl}}/facts?max_length=200&amp;limit=2",
                    "host": [
                        "{{baseUrl}}"
                    ],
                    "path": [
                        "facts"
                    ],
                    "query": [
                        {
                            "key": "max_length",
                            "value": "200"
                        },
                        {
                            "key": "limit",
                            "value": "2"
                        }
                    ]
                }
            },
            "status": "OK",
            "code": 200,
            "_postman_previewlanguage": "json",
            "header": [
                {
                    "key": "Content-Type",
                    "value": "application/json"
                }
            ],
            "cookie": [],
            "body": "[\n  {\n    \"fact\": \"ex ad\",\n    \"length\": 200\n  }, (...)}\n]"
        }
    ]
}
],
"description": "Cat Facts"

}
</pre>
        

        <strong>Hierarchy</strong>

        <p>Folders and requests are both items.  An item with no <code>request</code> property is rendered by the Postman UI as a Folder.  Items with a <code>request</code> property
        are rendered as requests.  Folders can contain other items, both folders and requests.  Requests are always leaf nodes, they cannot contain other items.</p>

        <p> A collection is the top level item in the hierarchy.  It can contain a tree of items, but cannot itself be contained.  </p>

*/
public class ItemElement extends CollectionElement {

    private String description;
    private ArrayList<EventElement> event = null;
    private RequestElement request = null;
    private ArrayList<ResponseElement> response = null;
    private ArrayList<ItemElement> item;
    private String name;
    private VariableListMap<PostmanVariable> variable = null;

    private transient ItemElement parent = null;
    
    


     /**
     * 
     * 
     * Construct an empty item with only a <code>name</code> property and assign it as a child of <code>parent</code>  
     * 
     * @param name  The name of the object
     * @param parent The ItemElement containing this item. 
     */
    public ItemElement(String name, ItemElement parent) {
        this(name);
        this.setParent(parent);

    }
   /**
    *  * Construct an empty item with only a <code>name</code>>; property.  Once added to a PostmanCollection, the Postman UI will render this object as an empty folder.

    * @param name The name of the object
    */
    
    public ItemElement(String name) {
        this.setName(name);
    }


    
    /** 
     * 
     * Return the value of the <code>description</code> property
     * 
     * @return String The description
     */
    public String getDescription() {
        return description;
    }

    
    /** 
     * 
     * Set the value of the <code>description</code> property
     * 
     * 
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /** 
     * 
     * Return the ArrayList&#60;EventElement&#62; containing the objects comprising the <code>event</code> array
     * 
     * @return ArrayList&#60;EventElement&#62;
     */
    public ArrayList<EventElement> getEvents() {

        return event;
    }

    
    /** 
     * 
     * Get an event, specifying whether to return the pre-request script or test script associated with this item. 
     * 
     * 
     * @param evtType Enumerated value for the event type, eg., pre-request or test.
     * @return EventElement The event, if it exists
     */
    public EventElement getEvent(enumEventType evtType) {
        if (event == null) {
            return null;
        }
        for (int i = 0; i < event.size(); i++) {
            if (event.get(i).getEventType() == evtType) {
                return event.get(i);
            }
        }
        return null;
    }

    
    /** 
     * Set the ArrayList&#60;EventElement&#62; comprising the values in the <code>event</code> array.
     * 
     * @param events
     */
    public void setEvents(ArrayList<EventElement> events) {
        this.event = events;
    }

    
    /** 
     * 
     * Return the object containing the values in the <code>request</code> property, or null if this item does not contain a request (e.g., is a folder);
     * 
     * 
     * @return RequestElement The request, or null if no request is defined.
     */
    public RequestElement getRequest() {
        return request;
    }

    
    /** 
     * 
     * Set the object containing the values in the <code>request</code> property
     * 
     * @param request
     */
    public void setRequest(RequestElement request) {
        this.request = request;
    }

    
    /** 
     * 
     * Return an ArrayList&#60;PostmanResponse&#62; containing the values in the <code>response</code> property array, or null if none are defined.
     * 
     * 
     * @return ArrayList&#60;{@link com.postman.collection.ResponseElement PostmanResponse}&#62;  The responses, or null if none are defined.
     */
    public ArrayList<ResponseElement> getResponses() {
        return response;
    }

    
    /** 
     * 
     * Set the ArrayList&#60;PostmanResponse&#62;  containing the values in the <code>response</code> property array.  Passing null to this method removes the response array
     * 
     * @param response
     */
    public void setResponses(ArrayList<ResponseElement> response) {
        this.response = response;
    }

    
    /** 
     * 
     * Return an ArrayList&#60;{@link com.postman.collection.ItemElement ItemElement}&#62; containing the tree of items owned by this item.
     * 
     *      * @return ArrayList&#60;{@link com.postman.collection.ItemElement ItemElement}&#62;  The items
     */
    public ArrayList<ItemElement> getItems() {
        return item;
    }

    /**
     * 
     * Recursively search the entire tree of items in the <code>item</code> property, optionally filter by item type (eg. FOLDER or REQUEST)
     * 
     * 
     * @param filter Enumerated value for the object type, eg., FOLDER or REQUEST.  Passing null returns all items.
     * @return
     */
    public ArrayList<ItemElement> getItems(enumItemElementType filter) {
        ArrayList<ItemElement> results = new ArrayList<ItemElement>();

        if (item == null) {
            return null;
        }

        for (ItemElement curItem : item) {
            if ((filter != null && curItem.getItemType() == filter) || (filter == null)) {
                results.add(curItem);
                
            }
            try {
                results.addAll(curItem.getItems(filter));

            } catch (Exception e) {

            }

        }

        return results;
    }

    
    /** 
     * 
     * Determine whether this item is a REQUEST or a FOLDER
     * 
     * 
     * @return enumItemElementType
     */
    public enumItemElementType getItemType() {
        if (this.request == null) {
            return enumItemElementType.FOLDER;
        } else {
            return enumItemElementType.REQUEST;
        }
    }

    
    /** 
     * 
     * Set the value of the <code>item</code> array with an ArrayList&#60;ItemElement&#62;.  Passing null effectively removes all children from this item.
     * 
     * @param items  The items, or null to remove all items.
     */
    public void setItems(ArrayList<ItemElement> items) {
        this.item = items;
    }

    
    /** 
     * 
     * Recursively search the contents of the <code>item</code> array for the item with the specified key.  Generally this is the <code>name</code> property for requests and folders.
     * 
     * @param key  The key (name) of the desired item
     * @return ItemElement The item, if it is found in the <code>item</code> array, or null if it is not.
     */
    public ItemElement getItem(String key) {
        return this.getItem(key, false, null);
    }

    
    /** 
     * @param key
     * @param filter
     * @return ItemElement
     */
    public ItemElement getItem(String key, enumItemElementType filter) {
        return this.getItem(key, false, filter);
    }

    
    /** 
     * @param key
     * @param parent
     * @return ItemElement
     */
    public ItemElement getItem(String key, boolean parent) {
        return this.getItem(key,parent,null);
    }

    
    /**
     * 
     * Recursively search the contents of the <code>item</code> array for the item with the specified key, optionally returning the item or it's parent item. 
     * 
     * 
     * @param key The key (name) of the desired item
     * @param parent True to return the parent of the item, if found, false to return the item itself. 
     * @param filter Optional, filter on object type, eg., FOLDER or REQUEST.  If null, do not filter 
     * @return ItemElement The item if present, or null
     */
    
    public ItemElement getItem(String key, boolean parent, enumItemElementType filter) {
        ItemElement result = null;
        if (this.item == null) {
            return null;
        }
        // recursively traverse items looking for name == key
        for (ItemElement curItem : item) {
            if (item == null)
                return null;
            if (curItem.getKey().equals(key)) {
                if (!parent && (filter == null || filter == curItem.getItemType())) {
                    result = curItem;
                } else if(parent && (filter == null || filter == curItem.getItemType())) {
                    result = (ItemElement) this;
                }
                break;
            } else {
                result = curItem.getItem(key, parent, filter);
                if (result != null) {
                    break;
                }
            }

        }

        return result;
    }

    
    /** 
     * 
     * Add a PostmanResponse object to the <code>response</code> array
     * 
     * @param resp The new response
     * 
     */
    public void addResponse(ResponseElement resp)  {
        if (this.response == null) {
            this.response = new ArrayList<ResponseElement>();
        }
        this.response.add(resp);
    }

    
    /** 
     * 
     * Add multiple items to this item.  
     * 
     * @param newItems  The items to add
     * 
     */
    public void addItems(ArrayList<ItemElement> newItems) throws RecursiveItemAddException, IllegalPropertyAccessException {
        if (this.item == null) {
            this.item = new ArrayList<ItemElement>();
        }
        for(ItemElement curItem : newItems) {
            this.addItem(curItem);
        }
        
    }

    
    /**
     * 
     * Set the parent of this item.
     *  
     * @param parent  The parent item.
     */
    public void setParent(ItemElement parent) {
        this.parent = parent;
    }

    
    /** 
     * 
     * Get the parent of this item, or null if one is not defined (eg. this item is a collection);
     * 
     * @return ItemElement The parent item.
     */
    public ItemElement getParent() {
        return this.parent;
    }

    
    
    
    /** 
     * 
     * Searches the direct children of this item (eg., non-recursively) to find an entry in the array that is the same Java instance as this item (Object.equals())
     * 
     * @param theItem  The item to search for
     * @return boolean
     */
    public boolean hasItem(ItemElement theItem) {
        if (item == null) {
            return false;
        }
        for (ItemElement curItem : item) {
            if (curItem.equals(theItem))
                ;
            return true;
        }

        return false;
    }

    
    /** 
     * 
     * Add an event to the array of items in the <code>event</code> property
     * 
     * @param newEvent
     */
    private void addEvent(EventElement newEvent) {
        if (event == null) {
            event = new ArrayList<EventElement>();
        }
        if (this.getEvent(newEvent.getEventType()) == null) {
            event.add(newEvent);
        } else {
            event.remove(this.getEvent(newEvent.getEventType()));
            event.add(newEvent);
        }
    }

    
    /** 
     * 
     * Append a new direct child item to the array of items in the <code>item</code> property.  This method does not recursively check for circular additions/references.
     * 
     * @param newItem The item to add 
     * @throws RecursiveItemAddException If newItem is the same item instance as this item.
     * @throws IllegalPropertyAccessException If this item is a request
     */
    public void addItem(ItemElement newItem) throws RecursiveItemAddException, IllegalPropertyAccessException {

        if (newItem.equals(this)) {
            throw new RecursiveItemAddException("Cannot add an object to itself, lolz");
        }
        if (this.getItemType() == enumItemElementType.REQUEST) {
            throw new IllegalPropertyAccessException("Cannot add items to Requests");
        }
        if (this.item == null) {
            this.item = new ArrayList<ItemElement>();
        }
        newItem.setParent(this);
        this.item.add(newItem);
        

    }

    
    /** 
     * 
     * 
     * Add a new direct child item to the array of items in the <code>item</code> property at the specified index.  This method does not recursively check for circular additions/references.
     * 
     * @param newItem  Item to add
     * @param position Index for new item
     * @throws IllegalPropertyAccessException If newItem is already a direct child of this item, or if position is &#60; 0 or &#62; the size of the existing array
     * @throws RecursiveItemAddException If newItem is already a child of this item
     *
     */
    public void addItem(ItemElement newItem, int position) throws IllegalPropertyAccessException, RecursiveItemAddException {
        if(this.item == null) {
            this.item = new ArrayList<ItemElement>();
        }
        if (this.hasItem(newItem)) {
            throw new IllegalPropertyAccessException("Item is already present");
        }
        // If the newitem already owns this item, it's a circular recursion
        if (newItem.getItem(this.getKey()) != null)

        {
            throw new RecursiveItemAddException("Item [" + newItem.getKey() + "] already contains this item [" + this.getKey());
        }
        if(position < 0 || position > this.item.size()) {
            throw new IllegalPropertyAccessException("Position " + position + " is out of bounds");
        }
        
        this.item.add(position, newItem);

    }

    
    /** 
     * 
     * Removes an item from the tree of items comprising the <code>item</code> property
     * 
     * @param oldItem The item to remove
     * 
     */
    public void removeItem(ItemElement oldItem)  {
        this.removeItem(oldItem.getKey());
    }

    /** 
     * 
     * Removes an item with the specified key from the tree of items comprising the <code>item</code> property
     * 
     * @param key Key of the item to remove (ie. it's name)
     * 
     */
    public void removeItem(String key) {
        if (item == null) {
            return;
        }
        for (ItemElement curItem : item) {
            if (curItem.getKey().equals(key)) {
                this.item.remove(curItem);
                break;
            }

        }

    }
    
    /** 
     * 
     * Set the pre-request script for this item in the <code>event</code> array
     * 
     * @param code The source code for the script
     * 
     */
    public void setPreRequestScript(String code)  {

        EventElement prEvent = new EventElement(enumEventType.PRE_REQUEST, code);
        this.addEvent(prEvent);
    }

    
    /** 
     * @return EventElement
     */
    public EventElement getPreRequestScript() {
        return this.getEvent(enumEventType.PRE_REQUEST);
    }

    
    /** 
     * @return EventElement
     */
    public EventElement getTestScript() {
        return this.getEvent(enumEventType.TEST);
    }

    
    /** 
     * Set the test script for this item in the <code>event</code> array
     * 
     * @param code The source code for the script
     * 
     */
    public void setTestScript(String code) throws Exception {

        EventElement prEvent = new EventElement(enumEventType.TEST, code);
        this.addEvent(prEvent);
    }

    
    
    /** 
     * @return String
     */
    public String getName() {
        return this.name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
/** 
     * @return String
     */
    @Override
    public String getKey() {

        // return this.key;
        return this.name;
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