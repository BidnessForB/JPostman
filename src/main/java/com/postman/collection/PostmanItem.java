package com.postman.collection;


import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
// foo
public class PostmanItem implements IPostmanCollectionElement  {
    private String description; 
    private PostmanEvent[] event = null;
    private PostmanRequest request = null;
    private PostmanResponse[] response = null;
    private PostmanItem[] item;
    private String name; 
    @Override
    public String getKey() {
        
        return this.getName();
    }

    @Override
    public void setKey(String key) {
        
        this.setName(key);
    }

    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        
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
        
        if(newItem.equals(this)) {
            throw new Exception("Cannot add an object to itself, lolz");
        }
        if(this.getItemType() == enumPostmanItemType.REQUEST)
        {
            throw new Exception("Cannot add items to Requests");
        }
       
        this.addItem(newItem, item == null ? 0 : item.length);

        try {
            this.toJson(false, null);
        }
        catch(Exception e) {
            System.out.println("rut roh");
        }

    }

    public boolean isValid() {
        return true;
    }

    

    public PostmanItem[] getItemsOfType(enumPostmanItemType ofType) {
        if(item == null)
        {
            return null;
        }
        List<PostmanItem> alItems = this.getItemsOfTypeImpl(ofType);
        PostmanItem curItem;
        PostmanItem[] retVal = new PostmanItem[alItems.size()];
        for(int i = 0; i < alItems.size(); i++)
        {
            curItem = (PostmanItem)alItems.get(i);
            retVal[i] = curItem;
        }
        
        return retVal;
       

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

    public void addItem(PostmanItem newItem, int position) throws Exception {
        if(this.hasItem(newItem))
        {
            throw new Exception ("Item is already present");
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
        ArrayList<PostmanItem> liItems = new ArrayList<PostmanItem>(Arrays.asList(new PostmanItem[0]));
        if(this.item == null)
        {
            liItems.add(newItem);
        }
        else{
            liItems = new ArrayList<PostmanItem>(Arrays.asList(this.item));
            liItems.add(position < liItems.size() ? position : liItems.size(), newItem);
        }


        
        item = liItems.toArray(new PostmanItem[0]);
        
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
