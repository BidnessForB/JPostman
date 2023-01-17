package com.postman.collection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import java.util.Set;
import java.util.Iterator;


import com.google.gson.Gson;




public class PostmanCollection extends PostmanItem 
{



private PostmanInfo info = null;
private PostmanVariable[] variable = null;
private PostmanAuth auth = null;
private ValidationMessage[] validationMessages;

public static void main( String[] args ) throws Exception
    {
        //NOTE: using "import java.io.File" produces a spurious warning in VSCode that the "Import is never used"
        //Thus, fully qualified class names and no imports
        String filePath = new java.io.File("").getAbsolutePath();
        // PostmanCollection pmcTest = PostmanCollection.PMCFactory(filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json");
        //PostmanCollection pmcTest = PostmanCollection.PMCFactory(filePath + "/src/main/resources/com/postman/collection/broken.postman_collection.json");
        //pmcTest.validate();
        PostmanCollection pmcTest = new PostmanCollection("Constructed Bodies");


        PostmanBody byUrlencoded = new PostmanBody(enumRequestBodyMode.URLENCODED);
        byUrlencoded.setFormdata("x-field-1", "value 1", "This is value 1");
        byUrlencoded.setFormdata("x-field-2", "value 2", "This is value 2");
        PostmanRequest rqUrlencoded = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqUrlencoded.setBody(byUrlencoded);
        pmcTest.addRequest(rqUrlencoded, "URLEncoded body");

        PostmanBody byPlainText = new PostmanBody(enumRequestBodyMode.TEXT);
        byPlainText.setRaw("This is some plain text");
        PostmanRequest rqPlainText = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqPlainText.setBody(byPlainText);
        pmcTest.addRequest(rqPlainText, "Text Body");

        PostmanBody byFormdata = new PostmanBody(enumRequestBodyMode.FORMDATA);
        byFormdata.setFormdata("field-1", "value 1", "This is value 1");
        byFormdata.setFormdata("field-2", "value 2", "This is value 2");
        PostmanRequest rqFormData = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqFormData.setBody(byFormdata);
        pmcTest.addRequest(rqFormData, "Formdata Body");

        PostmanBody byJsondata = new PostmanBody(enumRequestBodyMode.RAW, "{\"thing\":\"value\"}",enumRawBodyLanguage.JSON);
        PostmanRequest rqJsondata = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqJsondata.setBody(byJsondata);
        pmcTest.addRequest(rqJsondata, "JSON data Body");

        PostmanBody byHTML = new PostmanBody(enumRequestBodyMode.RAW, "{<html><body><p>This is some html</p</body></html>}",enumRawBodyLanguage.HTML);
        PostmanRequest rqHTML = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqHTML.setBody(byHTML);
        pmcTest.addRequest(rqHTML, "HTML data Body");

        PostmanBody byXML = new PostmanBody(enumRequestBodyMode.RAW, "{<xml><body><p>This is some XML</p</body></xml>}",enumRawBodyLanguage.XML);
        PostmanRequest rqXML = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqXML.setBody(byXML);
        pmcTest.addRequest(rqXML, "XML data Body");

        String strGraphQL = "{ \n            launchesPast(limit: 10) {\n              mission_name\n              launch_date_local\n              launch_site {\n                site_name_long\n              }\n              links {\n                article_link\n                video_link\n              }\n              rocket {\n                rocket_name\n              }\n            }\n          }";

        PostmanBody byGraphQL = new PostmanBody(enumRequestBodyMode.GRAPHQL, strGraphQL,enumRawBodyLanguage.GRAPHQL);
        //byGraphQL.addVariable(new PostmanVariable("{ \"limit\":2}");
        PostmanRequest rqGraphQL = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqGraphQL.setBody(byGraphQL);
        pmcTest.addRequest(rqGraphQL, "GraphQL data Body");
        
        pmcTest.writeToFile(filePath + "/test-output/constructed-bodies.json");
        System.out.println("Is valid: " + pmcTest.validate());

        System.out.println("break");

        
        /*
        pmcTest.writeToFile(filePath + "/test-output/example-catfact-compare.json");
        System.out.println("Foo");
       */
        /*

        List<PostmanUrl> liUrls  = new ArrayList<PostmanUrl>(Arrays.asList(new PostmanUrl[0]));
        

        
        liUrls.add(new PostmanUrl("http://foo.com/bar/bat.json"));
        liUrls.add(new PostmanUrl("//foo.com/bar/bat.json"));
        liUrls.add(new PostmanUrl("{{baseUrl}}/foo.com/bar/bat.json"));
        liUrls.add(new PostmanUrl("http://foo.com/bar/bat.json?foo=1&bar=2"));
        liUrls.add(new PostmanUrl("http://foo.com/bar/bat.json?foo=1&bar="));
        liUrls.add(new PostmanUrl("{{baseUrl}}/foo.com/bar/bat.json?foo=1&bar="));
        liUrls.add(new PostmanUrl("{{baseUrl}}/foo.com/bar/:path1/bat.json?foo=1&bar="));
        liUrls.add(new PostmanUrl("{{baseUrl}}foo.com:8080/bar/:path1/bat.json?foo=1&bar="));  
        liUrls.add(new PostmanUrl("{{baseUrl}}/foo.com:8080/bar/:path1/bat.json?foo=1&bar=")); 
        liUrls.add(new PostmanUrl("https://foo.com:8080/bar/:path1/bat.json?foo=1&bar="));
        liUrls.add(new PostmanUrl("https://foo.com/bar/:path1/bat.json?foo=1&bar="));

        PostmanCollection pmcTest2 = new PostmanCollection("URL Test");
        for(int i = 0; i<liUrls.size();i++)
        {
            pmcTest2.addRequest(new PostmanRequest(enumHTTPRequestMethod.GET,liUrls.get(i)),"URL " + (i + 1));
        } 

        
        
        pmcTest2.writeToFile(filePath +"/test-output/shouldCreateURLs.json");

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
       */
       
    }

public void moveItem(String itemToMoveKey, String parentKey) throws Exception {
    PostmanItem itemToMove = this.getItem(itemToMoveKey);
    PostmanItem parent = this.getItem(parentKey);
    
    if(itemToMove == null || parent == null)
    {
        throw new Exception("Couldn't find item to move and/or parent");
    }

}

public ValidationMessage[] getValidationMessage() {
    return this.validationMessages;
}


public boolean validate() throws Exception {
    
    String filePath = new java.io.File("").getAbsolutePath();
    String schemaJSON = null;;
    BufferedReader brItem;
    String strChunk;
    ObjectMapper mapper = new ObjectMapper();
    this.validationMessages = null;
    
    brItem = new BufferedReader(new FileReader(new File(filePath + "/src/main/resources/com/postman/collection/postman-collection-2.1-schema.json")));
        while((strChunk = brItem.readLine()) != null)
            schemaJSON = schemaJSON + strChunk;
        try {
            brItem.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    schemaJSON = schemaJSON.substring(4);
    JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
    JsonSchema schema = factory.getSchema(schemaJSON);
    JsonNode pmcNode = mapper.readTree(this.toJson(false, null));
    schema.initializeValidators();
    Set<ValidationMessage> errors = schema.validate(pmcNode);
    Iterator<ValidationMessage> itErrors = errors.iterator();
    
    if(errors.size() > 0) {
        this.validationMessages = errors.toArray(new ValidationMessage[0]);
    }

    while(itErrors.hasNext()) {
        System.out.println(itErrors.next().getMessage());
    }
    return(errors.size() == 0);

    
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
    newItem.setResponses(new PostmanResponse[0]);
}



public void addRequest(PostmanRequest newRequest,String name, int position) throws Exception {
    PostmanItem newItem = new PostmanItem(name);  
    newItem.setRequest(newRequest);
    super.addItem(newItem, position);
    newItem.setResponses(new PostmanResponse[0]);
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