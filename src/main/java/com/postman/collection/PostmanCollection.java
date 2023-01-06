package com.postman.collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Collections;
import java.lang.reflect.Array;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import com.google.gson.Gson;


public class PostmanCollection extends PostmanItem 
{


//private PostmanItem[] item = null;
private PostmanInfo info = null;
//private PostmanEvent[] event = null;
private PostmanVariable[] variable = null;
private PostmanAuth auth = null;
//private PostmanItem parent = null;

/*public PostmanItem[] getItems() {
    return item;
}

public enumPostmanItemType getItemType() {
    return enumPostmanItemType.COLLECTION;
}
*/
public static void main( String[] args ) throws Exception
    {
        String filePath = new File("").getAbsolutePath();
        PostmanCollection pmcTest = PostmanCollection.PMCFactory(filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json");
        PostmanCollection pmcWeather = PostmanCollection.PMCFactory(filePath +  "/src/main/resources/com/postman/collection/example-weather.postman_collection.json");
        PostmanCollection pmcFolders = PostmanCollection.PMCFactory(filePath +  "/folders-coll.json");
        String strRawItem = "";
        String strChunk;
        
        BufferedReader brItem = new BufferedReader(new FileReader(new File(filePath + "/src/main/resources/com/postman/collection/test-event-test.json")));
        while((strChunk = brItem.readLine()) != null)
            strRawItem = strRawItem + strChunk;
        try {
            brItem.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        PostmanEvent evt = PostmanEvent.pmcEventFactory(strRawItem);
        PostmanEvent evt2 = PostmanEvent.pmcEventFactory();
    

        PostmanItem newFolder1 = new PostmanItem("new Folder One");
        PostmanItem newFolder2 = new PostmanItem("new Folder Two");
        PostmanItem newFolder3 = new PostmanItem("new Folder Three");
        pmcTest.addItem(newFolder1);
        pmcTest.addItem(newFolder2);
        pmcTest.addItem(newFolder3);
        pmcTest.moveItem(newFolder2, newFolder1);
        pmcTest.moveItem(newFolder3, newFolder2);
//        pmcTest.moveItem(newFolder2, newFolder1);
      
        pmcTest.addCollection(pmcWeather, newFolder1);
        
        //pmcTest.addItem(pmcWeather, 2);
        
        
        //pmcTest.removeItem(newItem);
        //System.out.println("ITEM: " + newItem.getName() + " TYPE: " + newItem.getItemType());// + " PARENT: " + item.getParent());
       // System.out.println(pmcTest.toJson(false, null));
       pmcTest.setName("Cat-Weather"); 
       System.out.println("NAME: " + pmcTest.getName());
       
       PostmanItem item = pmcTest.getItem("Weather");
       item.setEvent(evt);
       item.setEvent(evt2);
       pmcTest.moveItem(item, pmcTest);
       pmcTest.writeToFile("new-coll.json");
       pmcFolders.writeToFile("folders-coll-2.json");
    }

public void moveItem(String itemToMoveKey, String parentKey) throws Exception {
    PostmanItem itemToMove = this.getItem(itemToMoveKey);
    PostmanItem parent = this.getItem(parentKey);
    
    if(itemToMove == null || parent == null)
    {
        throw new Exception("Couldn't find item to move and/or parent");
    }

}

public void moveItem(PostmanItem itemToMove, PostmanItem newParent) throws Exception {
    PostmanItem curParent = this.getItem(itemToMove.getKey(), true);
    
    if(curParent == null) {
        throw new Exception("Item parent not found");
    }
    curParent.removeItem(itemToMove);
    newParent.addItem(itemToMove);



}    
public void setVariables(PostmanVariable[] vars)
{
    this.variable = vars;
}

public void addCollection(PostmanCollection newColl, PostmanItem parent) throws Exception {
    this.addCollection(newColl, parent, true, true);
}
public void addCollection(PostmanCollection newColl, PostmanItem parent, boolean copyScripts, boolean copyVariables) throws Exception {
    if(!copyScripts)
    {
        newColl.setEvents(new PostmanEvent[0]);
    }
    parent.addItem(newColl);
    if (!copyVariables) {
        return;
    }

    List<PostmanVariable> resultList = new ArrayList<PostmanVariable>(this.getVariables().length + newColl.getVariables().length);
    Collections.addAll(resultList, this.getVariables());
    Collections.addAll(resultList, newColl.getVariables());

    @SuppressWarnings("unchecked")
    //the type cast is safe as the array1 has the type T[]
    PostmanVariable[] resultArray = (PostmanVariable[]) Array.newInstance(this.getVariables().getClass().getComponentType(), 0);
    this.setVariables(resultList.toArray(resultArray));
}

public void addCollection(PostmanCollection newColl) throws Exception
{
    this.addCollection(newColl, this);
}

public PostmanInfo getInfo() {
    return info;
}

public PostmanVariable[] getVariables() {
    return variable;
}

public PostmanAuth getAuth() {
    return auth;
}

public String getName() {
    return this.getInfo().getName() + "";
}

public void setName(String name) {
    this.getInfo().setName(name);
}

public String getDescription() {
    return this.getInfo().getDescription() + "";
}

public static PostmanCollection PMCFactory(String pathToJson) throws FileNotFoundException, IOException {
    PostmanCollection pmcRetVal = null;
    String strChunk = "";
    BufferedReader brItem = null;
    String strRawItem = "";
    Gson gson = null;
    

    brItem = new BufferedReader(new FileReader(new File(pathToJson)));
        while((strChunk = brItem.readLine()) != null)
            strRawItem = strRawItem + strChunk;
        try {
            brItem.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        gson = new Gson();
        pmcRetVal = gson.fromJson(strRawItem, PostmanCollection.class);
        
        
        
            


    return pmcRetVal;
}

public void writeToFile(String path) throws IOException {

    BufferedWriter writer = new BufferedWriter(new FileWriter(path));
    writer.write(this.toJson(false, null));   
    writer.close();
}



public PostmanItem getItemParent(String key) {
    return this.getItem(key, true);
}

/* public PostmanItem getItem(String key, boolean parent) {
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
 */
public Hashtable<String, PostmanRequest> getRequests() {
    return null;
}

@Override
public String getKey() {
    // TODO Auto-generated method stub
    return null;
}

@Override
public void setKey(String key) {
    // TODO Auto-generated method stub
    
}

}