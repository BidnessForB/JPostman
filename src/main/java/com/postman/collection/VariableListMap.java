package com.postman.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.lang.Iterable;



/**
 * 
 * 
 * Extends ArrayList with the ability to retrieve objects by key, and also forbids duplicate entries.
 * 
 * 
 * 
 * 
 */
public class VariableListMap<T> extends ArrayList<PostmanVariable> 
{
    private ArrayList<PostmanVariable> vars;

    public VariableListMap(ArrayList<PostmanVariable> vars) {
        super(vars);
    }

    public VariableListMap() {
        super(new ArrayList<PostmanVariable>());
    }

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

    public boolean containsKey(String key) {
        return(this.get(key) != null);
    }
  
    public PostmanVariable get(int index) {
        return super.get(index);
    }

    public boolean contains(PostmanVariable compare) {
        for(PostmanVariable curVar : this) {
            if(curVar.equals(compare)) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(PostmanVariable pvVar) {
        for(int i = 0; i < this.size(); i++)
        {
            if(this.get(i).getKey().equals(pvVar.getKey())){
                return i;
            }
        }
        return -1;
    }

    public boolean add(PostmanVariable pvVar) {
        int index;
        if(pvVar == null || (pvVar.getKey() == null && pvVar.getValue() == null)) {
            throw new NullPointerException("Key and Value properties are both null");
        }
        if(this.containsKey(pvVar.getKey())) {
            index = this.indexOf(pvVar);
            this.remove(pvVar.getKey());
            this.set(index, pvVar);

        }
        super.add(pvVar);
        return true;
    }

    public boolean addAll(VariableListMap<PostmanVariable> vars) {
        return this.addAll(this.size(), vars);
    }

public boolean addAll(int index, VariableListMap<PostmanVariable> vars) {
    boolean changed = false;
    
        for(int i = 0; i < vars.size(); i++) {
            if(!this.containsKey(vars.get(i).getKey())) {
                this.add(index, vars.get(i));
                changed = true;
            }
            else {
                this.remove(i);
                this.set(i, vars.get(i));
                changed = true;
            }
            
        }
        return changed;
}

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