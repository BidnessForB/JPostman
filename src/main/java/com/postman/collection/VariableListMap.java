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

    public boolean add(PostmanVariable pvVar) {
        if(pvVar == null || (pvVar.getKey() == null && pvVar.getValue() == null)) {
            throw new NullPointerException("Key and Value properties are both null");
        }
        super.add(pvVar);
        return true;
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

    public void set(PostmanVariable pvVar, int position) throws DuplicateVariableKeyException, IndexOutOfBoundsException {
        if(this.get(pvVar.getKey()) != null) {
            throw new DuplicateVariableKeyException("Variable with key [" + pvVar.getKey() + "] exists in this set");
        }
        super.set(position, pvVar);
    }
    
    
}