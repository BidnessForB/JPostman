package com.postman.collection;

import java.util.ArrayList;

public class PostmanItem extends PostmanCollectionElement {

    private String description;
    private ArrayList<PostmanEvent> event = null;
    private PostmanRequest request = null;
    private ArrayList<PostmanResponse> response = null;
    private ArrayList<PostmanItem> item;
    private String name;

    private transient PostmanItem parent = null;

    
    /** 
     * @return String
     */
    @Override
    public String getKey() {

        // return this.key;
        return this.name;
    }

    public PostmanItem(String name, PostmanItem parent) {
        this(name);
        this.setParent(parent);

    }

    public PostmanItem(String name) {
        this.setName(name);

    }

    public PostmanItem() {

    }

    
    /** 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    
    /** 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /** 
     * @return ArrayList<PostmanEvent>
     */
    public ArrayList<PostmanEvent> getEvents() {

        return event;
    }

    
    /** 
     * @param evtType
     * @return PostmanEvent
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
     * @param events
     */
    public void setEvents(ArrayList<PostmanEvent> events) {
        this.event = events;
    }

    
    /** 
     * @return PostmanRequest
     */
    public PostmanRequest getRequest() {
        return request;
    }

    
    /** 
     * @param request
     */
    public void setRequest(PostmanRequest request) {
        this.request = request;
    }

    
    /** 
     * @return ArrayList<PostmanResponse>
     */
    public ArrayList<PostmanResponse> getResponses() {
        return response;
    }

    
    /** 
     * @param response
     */
    public void setResponses(ArrayList<PostmanResponse> response) {
        this.response = response;
    }

    
    /** 
     * @return ArrayList<PostmanItem>
     */
    public ArrayList<PostmanItem> getItems() {
        return item;
    }

    
    /** 
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
     * @param items
     */
    public void setItems(ArrayList<PostmanItem> items) {
        this.item = items;
    }

    
    /** 
     * @param key
     * @return PostmanItem
     */
    public PostmanItem getItem(String key) {
        return this.getItem(key, false);
    }

    
    /** 
     * @param key
     * @param parent
     * @return PostmanItem
     */
    public PostmanItem getItem(String key, boolean parent) {
        PostmanItem result = null;
        if (this.item == null) {
            return null;
        }
        // recursively traverse items looking for name == key
        for (PostmanItem curItem : item) {
            // System.out.println("Parsing: " + this.getName() + " PARENT: " + parent);
            if (item == null)
                return null;
            if (curItem.getKey().equals(key)) {
                if (!parent) {
                    result = curItem;
                } else {
                    result = (PostmanItem) this;
                }

                break;
            } else {
                result = curItem.getItem(key, parent);
                if (result != null) {
                    break;
                }
            }

        }

        return result;
    }

    
    /** 
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
     * @param newItems
     * @throws Exception
     */
    public void addItems(ArrayList<PostmanItem> newItems) throws Exception {
        if (this.item == null) {
            this.item = new ArrayList<PostmanItem>();
        }
        this.item.addAll(newItems);
    }

    
    /** 
     * @param parent
     */
    public void setParent(PostmanItem parent) {
        this.parent = parent;
    }

    
    /** 
     * @return PostmanItem
     */
    public PostmanItem getParent() {
        return this.parent;
    }

    
    /** 
     * @param ofType
     * @return ArrayList<PostmanItem>
     */
    public ArrayList<PostmanItem> getItemsOfType(enumPostmanItemType ofType) {
        if (item == null) {
            return null;
        }
        ArrayList<PostmanItem> alItems = this.getItemsOfTypeImpl(ofType);
        return alItems;

    }

    
    /** 
     * @param theItem
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
     * @param ofType
     * @return ArrayList<PostmanItem>
     */
    private ArrayList<PostmanItem> getItemsOfTypeImpl(enumPostmanItemType ofType) {
        ArrayList<PostmanItem> results = new ArrayList<PostmanItem>();

        if (item == null) {
            return null;
        }

        for (PostmanItem curItem : item) {
            if (curItem.getItemType() == ofType) {
                results.add(curItem);
                // System.out.println(curItem.getName());
            }
            try {
                results.addAll(curItem.getItemsOfTypeImpl(ofType));

            } catch (Exception e) {

            }

        }

        return results;

    }

    
    /** 
     * @param newEvent
     */
    public void setEvent(PostmanEvent newEvent) {
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
     * @param newItem
     * @throws Exception
     */
    public void addItem(PostmanItem newItem) throws Exception {

        if (newItem.equals(this)) {
            throw new Exception("Cannot add an object to itself, lolz");
        }
        if (this.getItemType() == enumPostmanItemType.REQUEST) {
            throw new Exception("Cannot add items to Requests");
        }
        if (this.item == null) {
            this.item = new ArrayList<PostmanItem>();
        }
        this.item.add(newItem);

    }

    
    /** 
     * @param newItem
     * @param position
     * @throws Exception
     */
    public void addItem(PostmanItem newItem, int position) throws Exception {
        if (this.hasItem(newItem)) {
            throw new Exception("Item is already present");
        }
        // If the newitem already owns this item, it's a circular recursion
        if (newItem.getItem(this.getKey()) != null)

        {
            throw new Exception("Item [" + newItem.getKey() + "] already contains this item [" + this.getKey());
        }
        this.item.add(position, newItem);

    }

    
    /** 
     * @param oldItem
     * @throws Exception
     */
    public void removeItem(PostmanItem oldItem) throws Exception {
        this.removeItem(oldItem.getKey());
    }

    
    /** 
     * @param code
     * @throws Exception
     */
    public void setPreRequestScript(String code) throws Exception {

        PostmanEvent prEvent = new PostmanEvent(enumEventType.PRE_REQUEST, code);
        this.setEvent(prEvent);
    }

    
    /** 
     * @param code
     * @throws Exception
     */
    public void setTestScript(String code) throws Exception {

        PostmanEvent prEvent = new PostmanEvent(enumEventType.TEST, code);
        this.setEvent(prEvent);
    }

    
    /** 
     * @param code
     * @param type
     */
    public void setPreRequestScript(String code, String type) {

    }

    
    /** 
     * @param key
     * @throws Exception
     */
    public void removeItem(String key) throws Exception {
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

}
