package com.postman.collection;
import javax.lang.model.util.ElementScanner14;

import com.google.gson.Gson;

public class PostmanItem implements IPostmanCollectionElement  {
    private String description;
    private PostmanEvent[] event = null;
    private PostmanRequest request = null;
    private PostmanResponse[] response = null;
    private PostmanItem[] item;
    
    
    private String name;
    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        return this.getName();
    }

    @Override
    public void setKey(String key) {
        // TODO Auto-generated method stub
        this.setName(key);
    }

    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        // TODO Auto-generated method stub
        return new Gson().toJson(this);
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
            if (curItem.getName().equals(key))
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
    
  /*  
    public PostmanItem getItem(String key, boolean parent) { 
        System.out.println("TYPE: " + (this.getItemType() == enumPostmanItemType.FOLDER ? "FOLDER" : "REQUEST"));    
        if (item == null) {
            return null;
        }
        for(PostmanItem curItem: item) {
            if (curItem.getName().equals(key))
            {
                if (!parent)
                    return curItem;
                else
                    return this;
            }
            else
                return curItem.getItem(key, parent);

        }
        return null;
    }
    */
    public void addItem(PostmanItem newItem) throws Exception {
        if(item == null)
        {
            item = new PostmanItem[0];
        }
        this.addItem(newItem, item.length);
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

    public void addItem(PostmanItem newItem, int position) throws Exception {
        if(item == null)
        {
            item = new PostmanItem[0];
        }
        if(newItem.getClass().getName().equals("com.postman.collection.PostmanCollection"))
        {
            String clname = this.getClass().getName();
            System.out.println("CLASS " + clname);
            PostmanItem[] newItems = newItem.getItems();
            PostmanItem newFolder = new PostmanItem(newItem.getName());
            newFolder.setDescription(newItem.getDescription() + " IMPORTED Collection");
            this.addItem(newFolder, position);
            newFolder.setEvents(newItem.getEvents());
            for(int i = 0; i < newItems.length; i++)
            {
                newFolder.addItem(newItems[i]);
            }
            return;
            //throw new Exception("Can't add a collection to a collection");
        }
        if((position > (item.length) || position < 0)) {
            throw new Exception("Position " + position + " out of bounds.");
        }
        PostmanItem[] newArr = new PostmanItem[item.length + 1];
        for(int i = 0; i < item.length; i++)
        {
            if(i == position)
            {
                newArr[i] = newItem;
                newArr[i+1] = item[i];
            }
            else if (i < position)
            {
                newArr[i] = item[i];
            }
            else if (i > position)
            {
                newArr[i+1] = item[i];
            }
        }
        if(position == item.length) {
            newArr[position] = newItem;
        }
        item = newArr;
        
    }
    public void removeItem(PostmanItem oldItem) throws Exception
    {
        this.removeItem(oldItem.getKey());
    }

    public void removeItem(String key) throws Exception {
        // TODO Auto-generated method stub
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
