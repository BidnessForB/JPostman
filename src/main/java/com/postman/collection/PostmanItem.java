package com.postman.collection;


import com.google.gson.Gson;
import com.postman.collection.util.CollectionUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
// foo
public class PostmanItem implements IPostmanCollectionElement  {
    private String description; 
    private PostmanEvent[] event = null;
    private PostmanRequest request = null;
    private PostmanResponse[] response = null;
    private PostmanItem[] item;
    private String name; 
    private transient String key = UUID.randomUUID().toString();
    private transient PostmanItem parent = null;
    @Override
    public String getKey() {
        
        //return this.key;
        return this.name;
    }

    

    

    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        
        return new Gson().toJson(this);
    }

    
    public PostmanItem(String name, PostmanItem parent) {
        this(name);
        this.setParent(parent);

    }
    public PostmanItem(String name)
    {
        this.setName(name);
        
    }

    public PostmanItem() {
        
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public PostmanEvent[] getEvents() {
        if (event == null) {
            event = new PostmanEvent[0];
        }
        return event;
    }

    public PostmanEvent getEvent(enumEventType evtType) {
        PostmanEvent event;
        PostmanEvent[] events = this.getEvents();
        for(int i = 0; i < events.length; i++)
        {
            event = events[i];
            if (event.getEventType() == evtType)
            {
                return event;
            }
        }
        return null;
    }

    public void setEvents(PostmanEvent[] event) {
        this.event = event;
    }
    public PostmanRequest getRequest() {
        return request;
    }
    public void setRequest(PostmanRequest request) {
        this.request = request;
    }
    public PostmanResponse[] getResponses() {
        return response;
    }
    public void setResponses(PostmanResponse[] response) {
        this.response = response;
    }
    public PostmanItem[] getItems() {
        return item;
    }

    public enumPostmanItemType getItemType() {
        if(this.request == null)
        {
            return enumPostmanItemType.FOLDER;
        }
        else
        {
            return enumPostmanItemType.REQUEST;
        }
    }
    public void setItems(PostmanItem[] item) {
        if(item != null && item.length != 0)
        this.item = item;
    }

    public PostmanItem getItem(String key) {
        return this.getItem(key, false);
    }

    public PostmanItem getItem(String key, boolean parent) {
        PostmanItem result = null;
        if (this.item == null)
        {
            return null;
        }
        //recursively traverse items looking for name == key
        for (PostmanItem curItem: item) {
            System.out.println("Parsing: " + this.getName() + " PARENT: " + parent);
            if (item == null)
              return null;
            if (curItem.getKey().equals(key))
            {
                if (!parent) {
                    result = curItem;
                }
                else
                {
                    result = (PostmanItem) this;
                }
                
                break;
            }
            else
            {
                result = curItem.getItem(key, parent);
                if (result != null) {
                    break;
                }
            }
            
        }
    
        return result;
    }
   
   public void addResponse(PostmanResponse resp) throws Exception {
    
    
    this.response = CollectionUtils.insertInCopy((this.response == null ? new PostmanResponse[0] : this.response),resp);
    

   }

   


    public void addItems(PostmanItem[] newItems) throws Exception {
        for(int i = 0; i < newItems.length; i++)
        {
            this.addItem(newItems[i]);
        }
    }


    

    public boolean isValid() {
        return true;
    }

    public void setParent(PostmanItem parent)
    {
        this.parent = parent;
    }

    public PostmanItem getParent()
    {
        return this.parent;
    }

    

    public PostmanItem[] getItemsOfType(enumPostmanItemType ofType) {
        if(item == null)
        {
            return null;
        }
        List<PostmanItem> alItems = this.getItemsOfTypeImpl(ofType);
        return (alItems == null ? null : alItems.toArray(new PostmanItem[0]));
    
    }

    public boolean hasItem(PostmanItem theItem) {
        PostmanItem[] reqs = this.getItemsOfType(enumPostmanItemType.REQUEST);
        if(reqs != null)
        {
            for(PostmanItem curItem: reqs)
            {
                if(curItem.equals(theItem))
                {
                    return true;
                }
            }
        }
        PostmanItem[] flds = this.getItemsOfType(enumPostmanItemType.FOLDER);
        if(flds != null)
        {
            for(PostmanItem curItem: flds)
            {
                if(curItem.equals(theItem))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private List<PostmanItem> getItemsOfTypeImpl(enumPostmanItemType ofType)
    {
            ArrayList<PostmanItem> results = new ArrayList<PostmanItem>();
            

            if(item == null)
            {
                return null;
            }
            
            for (PostmanItem curItem: item)
            {
                if(curItem.getItemType() == ofType)
                {
                    results.add(curItem);
                    System.out.println(curItem.getName());
                }
                try
                {
                    results.addAll(curItem.getItemsOfTypeImpl(ofType));

                }
                catch(Exception e)
                {

                }
                
            }

            
            return results;

            
    }


    public void setEvent(PostmanEvent newEvent) {
        //will replace the script if it already exists. 
        
        

        if(this.getEvent(newEvent.getEventType()) == null)
        {
            PostmanEvent[] newArr = new PostmanEvent[1 + event.length]; 
            newArr[newArr.length - 1] = newEvent;
            event = newArr;
        }
        else {
            for(int i = 0; i < event.length; i++)
            {
                if(event[i].getEventType() == newEvent.getEventType()) {
                    event[i] = newEvent;
                }
            }
        }

    }

    public void addItem(PostmanItem newItem) throws Exception {
        
        if(newItem.equals(this)) {
            throw new Exception("Cannot add an object to itself, lolz");
        }
        if(this.getItemType() == enumPostmanItemType.REQUEST)
        {
            throw new Exception("Cannot add items to Requests");
        }
       
        this.addItem(newItem, item == null ? 0 : item.length);

    }

    public void addItem(PostmanItem newItem, int position) throws Exception {
        if(this.hasItem(newItem))
        {
            throw new Exception ("Item is already present");
        }
        //If the newitem already owns this item, it's a circular recursion
        if(newItem.getItem(this.getKey()) != null)

        {
            throw new Exception("Item [" + newItem.getKey() + "] already contains this item [" + this.getKey() );
        }
       
        this.item = CollectionUtils.insertInCopy((this.item == null ? new PostmanItem[0] : this.item), newItem, position);
         
    }
    public void removeItem(PostmanItem oldItem) throws Exception
    {
        this.removeItem(oldItem.getKey());
    }

    public void removeItem(String key) throws Exception {
        
        PostmanItem curItem;
        if(this.getItem(key) == null) {
            throw new Exception("Item " + key + " not found.");
        }
        PostmanItem[] newArr = new PostmanItem[item.length - 1];
        int newArrIndex = -1;
        for(int i = 0; i < item.length;)
        {
            curItem = item[i];
            if(!curItem.getKey().equals(key)) {
                newArr[++newArrIndex] = curItem;
            }   
            i++;
        }
        this.item = newArr;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){ 
        this.name = name;
    }


    


   
    
    
    
    
}
