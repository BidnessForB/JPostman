package com.postman.collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import java.util.Collections;
import java.lang.reflect.Array;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;



public class PostmanCollection extends PostmanItem 
{



private PostmanInfo info = null;
private PostmanVariable[] variable = null;
private PostmanAuth auth = null;

public static void main( String[] args ) throws Exception
    {
        //NOTE: using "import java.io.File" produces a spurious warning in VSCode that the "Import is never used"
        //Thus, fully qualified class names and no imports
        String filePath = new java.io.File("").getAbsolutePath();
        PostmanCollection pmcTest;
        
        PostmanUrl[] urls = new PostmanUrl[5];
        PostmanRequest[] requests = new PostmanRequest[5];
        
        
        urls[0] = new PostmanUrl("//foo.com/bar/bat.json");
        urls[1] = new PostmanUrl("//foo.com");
        urls[2] = new PostmanUrl("http://foo.com/bar/bat.json?foo=1&bar=2");
        urls[3] = new PostmanUrl("http://foo.com/");
        urls[4] = new PostmanUrl("http://foo.com");
        PostmanCollection pmcTest2 = new PostmanCollection("URL Test Constructed");
        for(int i = 0; i<urls.length; i++)
        {
            requests[i] = new PostmanRequest(enumHTTPRequestMethod.GET,urls[i]);
            pmcTest2.addRequest(requests[i],"Test Constructed URL " + i);
        } 

        
        
        pmcTest2.writeToFile(filePath +"/test-output/empty-coll-test.json");

        System.out.println("break");
        
        
        
        
        
        pmcTest = PostmanCollection.PMCFactory(filePath + "/src/main/resources/com/postman/collection/catfact-complete-coll.json");
        PostmanRequest pmrNewish = new PostmanRequest(enumHTTPRequestMethod.GET, "foo.com","");
        pmcTest.addRequest(pmrNewish, "Newish request");
        
         PostmanItem[] reqs = pmcTest.getItemsOfType(enumPostmanItemType.REQUEST);
        PostmanItem[] flds = pmcTest.getItemsOfType(enumPostmanItemType.FOLDER);
        System.out.println("Requests:\t\t" + reqs.length);
        System.out.println("Folders:\t\t" + flds.length);
       
        
        PostmanCollection pmcWeather = PostmanCollection.PMCFactory(filePath +  "/src/main/resources/com/postman/collection/example-weather.postman_collection.json");
        
        
        String strRawItem = "";
        String strChunk;
        
        BufferedReader brItem = new java.io.BufferedReader(new java.io.FileReader(new java.io.File(filePath + "/src/main/resources/com/postman/collection/test-event-test.json")));
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
        //newFolder3.addItem(newFolder2);
        System.out.println(pmcTest.toJson(false, null));
        pmcTest.moveItem(newFolder2, newFolder1);
        pmcTest.moveItem(newFolder3, newFolder2);
        //System.out.println(pmcTest.getItemsOfType(enumPostmanItemType.FOLDER).length);
        //System.out.println(pmcTest.hasItem(newFolder2));
//        pmcTest.moveItem(newFolder2, newFolder1);
      
        pmcTest.addCollection(pmcWeather, newFolder1);
        
        pmcTest.addItem(pmcWeather, 2);
        
        
        
        //System.out.println("ITEM: " + newItem.getName() + " TYPE: " + newItem.getItemType());// + " PARENT: " + item.getParent());
       // System.out.println(pmcTest.toJson(false, null));
       pmcTest.setName("Cat-Weather"); 
       System.out.println("NAME: " + pmcTest.getName());
       pmcTest.getItemsOfType(enumPostmanItemType.REQUEST);
       pmcTest.getItemsOfType(enumPostmanItemType.FOLDER);
       
       PostmanItem item = pmcTest.getItem("Weather");
       item.setEvent(evt);
       item.setEvent(evt2);
       pmcTest.moveItem(item, pmcTest);
       
       pmcTest.writeToFile("new-coll.json");
       
    }

public void moveItem(String itemToMoveKey, String parentKey) throws Exception {
    PostmanItem itemToMove = this.getItem(itemToMoveKey);
    PostmanItem parent = this.getItem(parentKey);
    
    if(itemToMove == null || parent == null)
    {
        throw new Exception("Couldn't find item to move and/or parent");
    }

}


public boolean isValid() {
    return true;
}

public void addFolder(PostmanItem newFolder) throws Exception {
    if(newFolder.getItemType() != enumPostmanItemType.FOLDER) {
        throw new Exception("Item is not a folder");
    }

    this.addItem(newFolder);

}



public void addRequest(PostmanRequest  newRequest, String name) throws Exception  {
    PostmanItem newItem = new PostmanItem(name);  
    newItem.setRequest(newRequest);
    super.addItem(newItem);
}



public void addRequest(PostmanRequest newRequest,String name, int position) throws Exception {
    PostmanItem newItem = new PostmanItem(name);  
    newItem.setRequest(newRequest);
    super.addItem(newItem, position);
}

public void moveItem(PostmanItem itemToMove, PostmanItem newParent) throws Exception {
    PostmanItem curParent = this.getItem(itemToMove.getKey(), true);
    if(itemToMove.equals(newParent))
    {
        throw new Exception("Can't move item to itself, yo");
    }
    
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

public  PostmanCollection(String name){
    this.info = new PostmanInfo();
    this.info.setName(name);
    this.setItems(new PostmanItem[0]);

}

public static PostmanCollection PMCFactory(String pathToJson) throws FileNotFoundException, IOException {
    PostmanCollection pmcRetVal = null;
    String strChunk = "";
    BufferedReader brItem = null;
    String strRawItem = "";
    com.google.gson.Gson gson = null;
    

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


@Override
public String getKey() {
    
    return null;
}

@Override
public void setKey(String key) {
    
    
}

}