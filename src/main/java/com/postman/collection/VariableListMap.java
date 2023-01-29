package com.postman.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.lang.Iterable;



/**
 * 
 * 
 * Extends ArrayList with Map like capabilities, including:
 * <ul>
 * <li>Support for retrieving by variable key which is always {@link com.postman.collection.PostmanVariable#getKey()}
 * <li>Support for retrieving by index
 * <li>Support for adding by index
 * <li>Duplicate keys are not allowed
 * <li>Null keys are allowed.
 * <li>Contains returns results based on {@link com.postman.collection.PostmanVariable#equals(Object)}
 * <li>Set will set the value of the variable in this ListMap if it exists, otherwise it will set the value of the specified <code>index</code>
 * <li>the <code>add</code> method returns false if the specified key already exists in the collection and does not change the collection
 * </ul>
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class VariableListMap<T> extends ArrayList<PostmanVariable> 
{
    /**
     * Returns a VariableListMap populated with the contents of <code>vars</code>
     * @param vars ArrayList&#60;PostmanVariable&#62; of variables
     */
    
    public VariableListMap(ArrayList<PostmanVariable> vars) {
        super(vars);
    }


    /**
     * 
     * Returns an empty <code>VariableListMap</code>
     */
    public VariableListMap() {
        super(new ArrayList<PostmanVariable>());
    }

    
    /** 
     * @param key
     * @return PostmanVariable
     */
    public PostmanVariable get(String key) {
        
        
        for(PostmanVariable curVar : this) {
            String curVarKey;
            if(key == null) {
                curVarKey = curVar.getKey();
                if(curVarKey == null) {
                    return curVar;
                }
            }
            else if (curVar.getKey() != null && curVar.getKey().equals(key))
                return curVar;
            }
            
        
        return null;
    }

    
    /** 
     * @param key
     * @return boolean
     */
    public boolean containsKey(String key) {
        return(this.get(key) != null);
    }
  
    
    /** 
     * @param index
     * @return PostmanVariable
     */
    public PostmanVariable get(int index) {
        return super.get(index);
    }

    
    /** 
     * @param compare
     * @return boolean
     */
    public boolean contains(PostmanVariable compare) {
        for(PostmanVariable curVar : this) {
            if(curVar.equals(compare)) {
                return true;
            }
        }
        return false;
    }

    
    /** 
     * @param pvVar
     * @return int
     */
    public int indexOf(PostmanVariable pvVar) {
        for(int i = 0; i < this.size(); i++)
        {
            if(this.get(i).getKey().equals(pvVar.getKey())){
                return i;
            }
        }
        return -1;
    }

    
    /** 
     * @param pvVar
     * @return boolean
     */
    public boolean add(PostmanVariable pvVar) {
        int index;
        if(pvVar == null || (pvVar.getKey() == null && pvVar.getValue() == null)) {
            throw new NullPointerException("Key and Value properties are both null");
        }
        if(this.containsKey(pvVar.getKey())) {
            return false;
        }
        else {
            super.add(pvVar);
            return true;
        }
        
    }

    
    /** 
     * @param vars
     * @return boolean
     */
    public boolean addAll(VariableListMap<PostmanVariable> vars) {
        return this.addAll(this.size(), vars);
    }


/** 
 * @param index
 * @param vars
 * @return boolean
 */
public boolean addAll(int index, VariableListMap<PostmanVariable> vars) {
    boolean changed = false;
    
        for(int i = 0; i < vars.size(); i++) {
            if(!this.containsKey(vars.get(i).getKey())) {
                this.add(index, vars.get(i));
                changed = true;
            }
            else {
                this.remove(i);
                this.add(i, vars.get(i));
                changed = true;
            }
            this.size();
        }
        return changed;
}

    
    /** 
     * @param key
     */
    public void remove(String key) {
        PostmanVariable curVar;
        for(int i = 0 ; i < this.size() ; i++) {
            curVar = this.get(i);
            if(key == null) {
                if(curVar.getKey() == null) {
                    this.remove(i);
                    break;
                }
            }
            else if(curVar.getKey() != null && curVar.getKey().equals(key)) {
                this.remove(i);
                break;
            }
        }
        
    }

    
    /** 
     * @param index
     * @param pvVar
     * @return PostmanVariable
     * @throws IndexOutOfBoundsException
     */
    public PostmanVariable set(int index, PostmanVariable pvVar) throws IndexOutOfBoundsException {
        //Disallow duplicate keys
        PostmanVariable retVal = null;
        int curIndex;

        if((this.containsKey(pvVar.getKey())  && this.indexOf(pvVar) == index) || !this.containsKey(pvVar.getKey())) {
            retVal = super.set(index, pvVar);
        }
        else if(this.containsKey(pvVar.getKey()) && this.indexOf(pvVar) != index) {
            curIndex = this.indexOf(pvVar.getKey());
            retVal = super.remove(curIndex);
            super.add(curIndex, pvVar);
        }
        return retVal;
        
    }
    
    
}