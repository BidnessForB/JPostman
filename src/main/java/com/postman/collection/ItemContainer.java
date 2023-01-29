package com.postman.collection;
import java.util.ArrayList;
public abstract class ItemContainer extends ItemElement {
    private ArrayList<ItemElement> item;


    /** 
     * 
     * Return an ArrayList&#60;{@link com.postman.collection.ItemElement ItemElement}&#62; containing the tree of items owned by this item.
     * 
     *      * @return ArrayList&#60;{@link com.postman.collection.ItemElement ItemElement}&#62;  The items
     */
    public ArrayList<ItemElement> getItemElements() {
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
    public ArrayList<ItemElement> getItemElements(enumItemElementType filter) {
        ArrayList<ItemElement> results = new ArrayList<ItemElement>();

        if (item == null) {
            return null;
        }

        for (ItemElement curItem : item) {
            if(filter == null && !(curItem instanceof ItemContainer) ) {
                results.add(curItem);
            }
            else if(filter == null && curItem instanceof ItemContainer) {
                results.addAll(((ItemContainer)curItem).getItemElements(filter));
            }
            else if (filter != null && filter == enumItemElementType.FOLDER && curItem instanceof Folder) {
                results.add(curItem);
                try {
                results.addAll(((Folder)curItem).getItemElements(filter));
    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if((filter != null && filter == enumItemElementType.REQUEST && curItem instanceof Request)) {
                results.add(curItem);
            }
            

        }

        return results;
    }

    /** 
     * 
     * Set the value of the <code>item</code> array with an ArrayList&#60;ItemElement&#62;.  Passing null effectively removes all children from this item.
     * 
     * @param items  The items, or null to remove all items.
     */
    public void setItemElements(ArrayList<ItemElement> items) {
        this.item = items;
    }

    
    /** 
     * 
     * Recursively search the contents of the <code>item</code> array for the item with the specified key.  Generally this is the <code>name</code> property for requests and folders.
     * 
     * @param key  The key (name) of the desired item
     * @return ItemElement The item, if it is found in the <code>item</code> array, or null if it is not.
     */
    public ItemElement getItemElement(String key) {
        return this.getItemElement(key,  null);
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
    
    public ItemElement getItemElement(String key, enumItemElementType filter) {
        ItemElement result = null;
        if (this.item == null) {
            return null;
        }
        // recursively traverse items looking for name == key
        for (ItemElement curItem : item) {
            if (item == null)
                return null;
            if(filter == null && curItem.getKey().equals(key)) {
                result = curItem;
                break;
            }
            if (curItem.getKey().equals(key)) {
                if (filter != null && (filter == enumItemElementType.REQUEST) && curItem instanceof Request) {
                    result = curItem;
                } else if(filter != null && filter == enumItemElementType.FOLDER &&  curItem instanceof Folder) {
                    result = (ItemElement)this;
                }
                break;
            } else if (curItem instanceof ItemContainer) {
                result = ((ItemContainer)curItem).getItemElement(key, filter);
                if (result != null) {
                    break;
                }
            }

        }

        return result;
    }
/** 
     * 
     * Add multiple items to this item.  
     * 
     * @param newItems  The items to add
     * 
     */
    public void addItemElements(ArrayList<ItemElement> newItems) throws RecursiveItemAddException, IllegalPropertyAccessException {
        if (this.item == null) {
            this.item = new ArrayList<ItemElement>();
        }
        for(ItemElement curItem : newItems) {
            this.addItemElement(curItem);
        }
        
    }

    /** 
     * 
     * Searches the direct children of this item (eg., non-recursively) to find an entry in the array that is the same Java instance as this item (Object.equals())
     * 
     * @param theItem  The item to search for
     * @return boolean
     */
    public boolean hasItemElement(ItemElement theItem) {
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
     * Append a new direct child item to the array of items in the <code>item</code> property.  This method does not recursively check for circular additions/references.
     * 
     * @param newItem The item to add 
     * @throws RecursiveItemAddException If newItem is the same item instance as this item.
     * @throws IllegalPropertyAccessException If this item is a request
     */
    public void addItemElement(ItemElement newItem) throws RecursiveItemAddException, IllegalPropertyAccessException {

        if (newItem.equals(this)) {
            throw new RecursiveItemAddException("Cannot add an object to itself, lolz");
        }
        
        if (this.item == null) {
            this.item = new ArrayList<ItemElement>();
        }
        newItem.setParent(this);
        this.item.add(newItem);
        

    }

    public ItemContainer(String name) {
        super(name);
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
    public void addItemElement(ItemElement newItem, int position) throws IllegalPropertyAccessException, RecursiveItemAddException {
        if(this.item == null) {
            this.item = new ArrayList<ItemElement>();
        }
        if (this.hasItemElement(newItem)) {
            throw new IllegalPropertyAccessException("Item is already present");
        }
        // If the newitem already owns this item, it's a circular recursion
        if (newItem instanceof ItemContainer && ((ItemContainer)newItem).getItemElement(this.getKey()) != null)

        {
            throw new RecursiveItemAddException("Item [" + newItem.getKey() + "] already contains this item [" + this.getKey());
        }
        if(position < 0 || position > this.item.size()) {
            throw new IllegalPropertyAccessException("Position " + position + " is out of bounds");
        }
        
        this.item.add(position, newItem);
        newItem.setParent(this);

    }

    
    /** 
     * 
     * Removes an item from the tree of items comprising the <code>item</code> property
     * 
     * @param oldItem The item to remove
     * 
     */
    public void removeItemElement(ItemElement oldItem)  {
        this.removeItemElement(oldItem.getKey());
    }

    /** 
     * 
     * Removes an item with the specified key from the tree of items comprising the <code>item</code> property
     * 
     * @param key Key of the item to remove (ie. it's name)
     * 
     */
    public void removeItemElement(String key) {
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

    public Request getRequest(String key) {
        return (Request)this.getItemElement(key, enumItemElementType.REQUEST);
    }

    public Folder getFolder(String key) {
        return (Folder)this.getItemElement(key, enumItemElementType.FOLDER);
    }

    



}
