package com.postman.collection;

import java.util.ArrayList;
    /**
     * 
     * Encapsulates the <code>item</code> object in the postman schema
<p></p>
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
        

        <h3>Hierarchy</h3>

        <p>Folders and requests are both items.  An item with no <code>request</code> property is rendered by the Postman UI as a Folder.  Items with a <code>request</code> property
        are rendered as requests.  Folders can contain other items, both folders and requests.  Requests are always leaf nodes, they cannot contain other items.</p>

        <p> A collection is the top level item in the hierarchy.  It can contain a tree of items, but cannot itself be contained.  </p>

*/
public class PostmanItem extends PostmanCollectionElement {

    private String description;
    private ArrayList<PostmanEvent> event = null;
    private PostmanRequest request = null;
    private ArrayList<PostmanResponse> response = null;
    private ArrayList<PostmanItem> item;
    private String name;

    private transient PostmanItem parent = null;
    
    


        /**
     * 
     * 
     * Construct an empty item with only a <name> property </name> and assign it as a child of <parent>  
     * 
     * @param name  The name of the object
     * @param parent The PostmanItem containing this item. 
     */
    public PostmanItem(String name, PostmanItem parent) {
        this(name);
        this.setParent(parent);

    }
   /**
    *  * Construct an empty item with only a <name> property </name>.  Once added to a PostmanCollection, the Postman UI will render this object as an empty folder.

    * @param name The name of the object
    */
    
    public PostmanItem(String name) {
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
     * Return the ArrayList&#60;PostmanEvent&#62; containing the objects comprising the <code>event</code> array
     * 
     * @return ArrayList<PostmanEvent>
     */
    public ArrayList<PostmanEvent> getEvents() {

        return event;
    }

    
    /** 
     * 
     * Get an event, specifying whether to return the pre-request script or test script associated with this item. 
     * 
     * 
     * @param evtType Enumerated value for the event type, eg., pre-request or test.
     * @return PostmanEvent The event, if it exists
     */
    public PostmanEvent getEvent(enumEventType evtType) {
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
     * Set the ArrayList&#60;PostmanEvent&#62; comprising the values in the <code>event</code> array.
     * 
     * @param events
     */
    public void setEvents(ArrayList<PostmanEvent> events) {
        this.event = events;
    }

    
    /** 
     * 
     * Return the object containing the values in the <code>request</code> property, or null if this item does not contain a request (e.g., is a folder);
     * 
     * 
     * @return PostmanRequest The request, or null if no request is defined.
     */
    public PostmanRequest getRequest() {
        return request;
    }

    
    /** 
     * 
     * Set the object containing the values in the <code>request</code> property
     * 
     * @param request
     */
    public void setRequest(PostmanRequest request) {
        this.request = request;
    }

    
    /** 
     * 
     * Return an ArrayList&#60;PostmanResponse&#62; containing the values in the <code>response</code> property array, or null if none are defined.
     * 
     * 
     * @return ArrayList<PostmanResponse>  The responses, or null if none are defined.
     */
    public ArrayList<PostmanResponse> getResponses() {
        return response;
    }

    
    /** 
     * 
     * Set the ArrayList&#60;PostmanResponse&#62;  containing the values in the <code>response</code> property array.  Passing null to this method removes the response array
     * 
     * @param response
     */
    public void setResponses(ArrayList<PostmanResponse> response) {
        this.response = response;
    }

    
    /** 
     * 
     * Return an ArrayList&#60;PostmanItem&#62; containing the tree of items owned by this item.
     * 
     * 
     * @return ArrayList<PostmanItem>  The items
     */
    public ArrayList<PostmanItem> getItems() {
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
    public ArrayList<PostmanItem> getItems(enumPostmanItemType filter) {
        ArrayList<PostmanItem> results = new ArrayList<PostmanItem>();

        if (item == null) {
            return null;
        }

        for (PostmanItem curItem : item) {
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
     * @return enumPostmanItemType
     */
    public enumPostmanItemType getItemType() {
        if (this.request == null) {
            return enumPostmanItemType.FOLDER;
        } else {
            return enumPostmanItemType.REQUEST;
        }
    }

    
    /** 
     * 
     * Set the value of the <code>item</code> array with an ArrayList&#60;PostmanItem&#62;.  Passing null effectively removes all children from this item.
     * 
     * @param items  The items, or null to remove all items.
     */
    public void setItems(ArrayList<PostmanItem> items) {
        this.item = items;
    }

    
    /** 
     * 
     * Recursively search the contents of the <code>item</code> array for the item with the specified key.  Generally this is the <code>name</code> property for requests and folders.
     * 
     * @param key  The key (name) of the desired item
     * @return PostmanItem The item, if it is found in the <code>item</code> array, or null if it is not.
     */
    public PostmanItem getItem(String key) {
        return this.getItem(key, false, null);
    }

    public PostmanItem getItem(String key, enumPostmanItemType filter) {
        return this.getItem(key, false, filter);
    }

    public PostmanItem getItem(String key, boolean parent) {
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
     * @return PostmanItem The item if present, or null
     */
    
    public PostmanItem getItem(String key, boolean parent, enumPostmanItemType filter) {
        PostmanItem result = null;
        if (this.item == null) {
            return null;
        }
        // recursively traverse items looking for name == key
        for (PostmanItem curItem : item) {
            if (item == null)
                return null;
            if (curItem.getKey().equals(key)) {
                if (!parent && (filter == null || filter == curItem.getItemType())) {
                    result = curItem;
                } else if(parent && (filter == null || filter == curItem.getItemType())) {
                    result = (PostmanItem) this;
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
     * @param resp
     * @throws Exception
     */
    public void addResponse(PostmanResponse resp) throws Exception {
        if (this.response == null) {
            this.response = new ArrayList<PostmanResponse>();
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
    public void addItems(ArrayList<PostmanItem> newItems)  {
        if (this.item == null) {
            this.item = new ArrayList<PostmanItem>();
        }
        this.item.addAll(newItems);
    }

    
    /**
     * 
     * Set the parent of this item.
     *  
     * @param parent  The parent item.
     */
    public void setParent(PostmanItem parent) {
        this.parent = parent;
    }

    
    /** 
     * 
     * Get the parent of this item, or null if one is not defined (eg. this item is a collection);
     * 
     * @return PostmanItem The parent item.
     */
    public PostmanItem getParent() {
        return this.parent;
    }

    
    
    
    /** 
     * 
     * Searches the direct children of this item (eg., non-recursively) to find an entry in the array that is the same Java instance as this item (Object.equals())
     * 
     * @param theItem  The item to search for
     * @return boolean
     */
    public boolean hasItem(PostmanItem theItem) {
        if (item == null) {
            return false;
        }
        for (PostmanItem curItem : item) {
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
    private void setEvent(PostmanEvent newEvent) {
        if (event == null) {
            event = new ArrayList<PostmanEvent>();
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
     * @throws IllegalPropertyAccessExeption If this item is a request
     */
    public void addItem(PostmanItem newItem) throws Exception {

        if (newItem.equals(this)) {
            throw new RecursiveItemAddException("Cannot add an object to itself, lolz");
        }
        if (this.getItemType() == enumPostmanItemType.REQUEST) {
            throw new IllegalPropertyAccessException("Cannot add items to Requests");
        }
        if (this.item == null) {
            this.item = new ArrayList<PostmanItem>();
        }
        this.item.add(newItem);

    }

    
    /** 
     * 
     * 
     * Add a new direct child item to the array of items in the <code>item</code> property at the specified index.  This method does not recursively check for circular additions/references.
     * 
     * @param newItem  Item to add
     * @param position Index for new item
     * @throws IllegalPropertyAccessException If newItem is already a direct child of this item, or if position is < 0 or > the size of the existing array
     * @throws RecursiveItemAddException If newItem is already a child of this item
     *
     */
    public void addItem(PostmanItem newItem, int position) throws Exception {
        if(this.item == null) {
            this.item = new ArrayList<PostmanItem>();
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
    public void removeItem(PostmanItem oldItem)  {
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
        for (PostmanItem curItem : item) {
            if (curItem.getKey().equals(key)) {
                this.item.remove(curItem);
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

        PostmanEvent prEvent = new PostmanEvent(enumEventType.PRE_REQUEST, code);
        this.setEvent(prEvent);
    }

    
    /** 
     * Set the test script for this item in the <code>event</code> array
     * 
     * @param code The source code for the script
     * 
     */
    public void setTestScript(String code) throws Exception {

        PostmanEvent prEvent = new PostmanEvent(enumEventType.TEST, code);
        this.setEvent(prEvent);
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

}
